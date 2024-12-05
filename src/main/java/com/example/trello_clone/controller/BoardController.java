package com.example.trello_clone.controller;

import com.example.trello_clone.Board;
import com.example.trello_clone.dto.BoardDTO;
import com.example.trello_clone.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    // Get all boards
    @GetMapping
    public List<BoardDTO> getAllBoards() {
        // Fetch all boards and convert them to BoardDTO
        return boardRepository.findAll().stream()
                .map(BoardDTO::from)  // Convert each Board entity to BoardDTO
                .collect(Collectors.toList());
    }

    // Create a new board
    @PostMapping
    public BoardDTO createBoard(@RequestBody BoardDTO boardDTO) {
        // Convert BoardDTO to Board entity, save it, and return the BoardDTO
        Board board = new Board();
        board.setName(boardDTO.getName());
        Board savedBoard = boardRepository.save(board);
        
        return BoardDTO.from(savedBoard);
    }

    // Get a board by ID
    @GetMapping("/{id}")
    public BoardDTO getBoardById(@PathVariable Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found with id " + id));

        return BoardDTO.from(board);  // Convert to BoardDTO before returning
    }

    // Delete a board by ID
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}
