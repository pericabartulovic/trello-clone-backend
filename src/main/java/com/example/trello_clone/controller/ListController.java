package com.example.trello_clone.controller;

import com.example.trello_clone.TrelloList;
import com.example.trello_clone.Board;
import com.example.trello_clone.repository.ListRepository;
import com.example.trello_clone.repository.BoardRepository;
import com.example.trello_clone.repository.CardRepository;
import com.example.trello_clone.dto.TrelloListDTO;  // Import DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import com.example.trello_clone.service.CardService;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping("/api/boards/{boardId}/lists")
public class ListController {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private CardRepository cardRepository;

    // Get all lists associated with a specific board
    @GetMapping
    public List<TrelloListDTO> getListsByBoardId(@PathVariable Long boardId) {
         // Ensure the board exists with the given boardId (Optional)
        boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board not found with id " + boardId));
            
        // Fetch all lists associated with the specified board and convert them to DTO
        return listRepository.findByBoardId(boardId).stream()
                .map(TrelloListDTO::from)  // Use the from method to convert to TrelloListDTO
                .collect(Collectors.toList());
    }

    // Create a new list for a specific board
    @PostMapping
    public TrelloListDTO createList(@PathVariable Long boardId, @RequestBody TrelloListDTO newListDTO) {
        // Ensure the board exists with the given boardId
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with id " + boardId));

        // Convert the DTO back to a TrelloList entity
        TrelloList newList = new TrelloList();
        newList.setName(newListDTO.getName());

        newList.setBoard(board);

        // Save the new list
        TrelloList savedList = listRepository.save(newList);

        // Return the saved list as a DTO
        return TrelloListDTO.from(savedList);
    }
    
    @DeleteMapping("/{listId}")
    @Transactional
    public void deleteListAndCards(@PathVariable Long boardId, @PathVariable Long listId) {
        // Ensure the list exists
        TrelloList list = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found with id " + listId));

        // Ensure the board exists
        boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board not found with id " + boardId));

        // Check if there are any cards in the list before deleting
        if (!list.getCards().isEmpty()) {
            // Delete all cards associated with the list
            cardRepository.deleteByListId(listId);
        }

        // Delete the list itself
        listRepository.deleteById(listId);
    }
    
    //Test deleting single card
    @Autowired
    private CardService cardService;

    // Delete a single card within a specific list
    @DeleteMapping("/{listId}/{cardId}")
    public ResponseEntity<Void> deleteSingleCard(@PathVariable Long listId, @PathVariable Long cardId) {
        cardService.deleteSingleCard(cardId); // Delegates the deletion to the service
        return ResponseEntity.ok().build();
    }
}
