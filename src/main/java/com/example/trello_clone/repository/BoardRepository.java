package com.example.trello_clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trello_clone.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
