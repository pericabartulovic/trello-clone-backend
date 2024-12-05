package com.example.trello_clone.dto;

import com.example.trello_clone.TrelloList;

public class TrelloListDTO {

    private Long id;
    private String name;

    // Default constructor
    public TrelloListDTO() {}

    // Constructor for convenience
    public TrelloListDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    // The from method to convert a TrelloList entity into a TrelloListDTO
    public static TrelloListDTO from(TrelloList trelloList) {
        return new TrelloListDTO(
                trelloList.getId(),
                trelloList.getName()
        );
    }
}
