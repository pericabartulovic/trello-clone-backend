package com.example.trello_clone.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.trello_clone.repository.CardRepository;
import com.example.trello_clone.repository.ListRepository;  // Add this import for TrelloList repository
import com.example.trello_clone.service.CardService;
import com.example.trello_clone.Card;
import com.example.trello_clone.dto.CardDTO;
import com.example.trello_clone.TrelloList;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardRepository cardRepository;
    private final ListRepository ListRepository;  // Inject TrelloList repository
    
    @Autowired
    private CardService cardService;

    public CardController(CardRepository cardRepository, ListRepository ListRepository) {
        this.cardRepository = cardRepository;
        this.ListRepository = ListRepository;
    }

    // Create a new card
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO) {
        // Fetch the TrelloList by its listId
        TrelloList trelloList = ListRepository.findById(cardDTO.getListId())
                .orElseThrow(() -> new RuntimeException("List not found"));

        // Create a new Card entity and set values
        Card card = new Card();
        card.setName(cardDTO.getTitle());  // Assuming title in DTO is mapped to name in entity
        card.setList(trelloList);  // Set the TrelloList object instead of listId
        card.setDueDate(cardDTO.getDueDate());

        // Save the card in the database
        Card savedCard = cardRepository.save(card);

        // Map savedCard entity back to CardDTO
        Long listId = savedCard.getList() != null ? savedCard.getList().getId() : null;
        CardDTO savedCardDTO = new CardDTO(
            savedCard.getId(),
            savedCard.getName(),
            listId,
            savedCard.getDueDate()
        );

        return ResponseEntity.ok(savedCardDTO);
    }

    // Get cards by listId
    @GetMapping("/{listId}")
    public ResponseEntity<List<CardDTO>> getCardsByListId(@PathVariable Long listId) {
        // Fetch cards from the database by listId
        List<Card> cards = cardRepository.findByListId(listId);

        // Convert each Card entity to CardDTO
        List<CardDTO> cardDTOs = cards.stream()
                .map(card -> {
                    Long listIdInCard = card.getList() != null ? card.getList().getId() : null;
                    return new CardDTO(
                            card.getId(),
                            card.getName(),
                            listIdInCard,
                            card.getDueDate()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(cardDTOs);
    }
    
     // Delete a single card
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteSingleCard(@PathVariable Long cardId) {
        cardService.deleteSingleCard(cardId); // Delegates the deletion to the service
        return ResponseEntity.ok().build();
    }
}
