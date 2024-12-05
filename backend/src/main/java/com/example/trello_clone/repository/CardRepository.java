package com.example.trello_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;

import com.example.trello_clone.Card;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByListId(Long listId);
    @Modifying
    @Transactional // Ensure this is transactional
    // @Query("DELETE FROM Card c WHERE c.list.id = :listId")
    void deleteByListId(Long listId);
}
