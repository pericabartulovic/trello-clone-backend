package com.example.trello_clone.service;

import com.example.trello_clone.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Transactional // Ensures this operation happens in a transactional scope
    public void deleteSingleCard(Long cardId) {
        // Check if card exists
        if (!cardRepository.existsById(cardId)) {
            throw new RuntimeException("Card not found with id " + cardId);
        }
        
        cardRepository.deleteById(cardId);
    }
}