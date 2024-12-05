package com.example.trello_clone.dto;

import com.example.trello_clone.Board;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDTO {

    private Long id;
    private String name;
    private List<TrelloListDTO> lists;

    // Default constructor
    public BoardDTO() {}

    // Constructor for convenience without the createdAt field
    public BoardDTO(Long id, String name, List<TrelloListDTO> lists) {
        this.id = id;
        this.name = name;
        this.lists = lists;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrelloListDTO> getLists() {
        return lists;
    }

    public void setLists(List<TrelloListDTO> lists) {
        this.lists = lists;
    }

    // The from method to convert a Board entity into a BoardDTO
    public static BoardDTO from(Board board) {
        // Convert each TrelloList entity to TrelloListDTO (without the createdAt field)
        List<TrelloListDTO> listDTOs = board.getLists().stream()
            .map(trelloList -> new TrelloListDTO(trelloList.getId(), trelloList.getName()))  // Removed createdAt
            .collect(Collectors.toList());

        // Return the BoardDTO without createdAt
        return new BoardDTO(
                board.getId(),
                board.getName(),
                listDTOs
        );
    }
}
