package com.bbogdandy.pinboard.repository;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.CalendarBoard;
import com.bbogdandy.pinboard.model.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAll();
    Board save(Board board);
    Board save(CalendarBoard board);
    Board save(KanbanBoard board);
    Board findById(long id);
}
