package com.example.trello_clone;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    // One Board can have many TrelloLists, so add the relationship
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Handles serialization of lists in Board
    private List<TrelloList> lists = new ArrayList<>();; // Initialize with ArrayList
    
    // Constructor, getters, and setters
    public Board() {}
    
    public Board(String name) {
        this.name = name;
        this.lists = new ArrayList<>();
    }

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

    public List<TrelloList> getLists() {
        return lists;
    }

    public void setLists(List<TrelloList> lists) {
        this.lists = lists;
    }

    // Add a list to the board
    public void addList(TrelloList list) {
        this.lists.add(list);
        list.setBoard(this); // Set the back-reference to the board
    }
}
