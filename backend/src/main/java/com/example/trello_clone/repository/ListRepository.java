package com.example.trello_clone.repository;

import com.example.trello_clone.TrelloList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListRepository extends JpaRepository<TrelloList, Long> {
    List<TrelloList> findByBoardId(Long boardId);
}
