package com.example.trello_clone;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    // Many cards can belong to a single list
    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private TrelloList list;

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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TrelloList getList() {
        return list;
    }

    public void setList(TrelloList list) {
        this.list = list;
    }
}
