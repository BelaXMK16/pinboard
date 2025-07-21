package com.bbogdandy.pinboard.controller;

import com.bbogdandy.pinboard.dto.NewBoardRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> getAllBoards() {

        return boardService.allBoards();
    }

    @PostMapping("/add")
    public ResponseEntity<Board> createBoard(@RequestBody NewBoardRequest request) {
        Board board = new Board();
        board.setName(request.getName());
        board.setPublic(request.isPublic());
        log.info("Creating Board {}", board.toString());
        Board saved = boardService.saveBoard(board);
        return ResponseEntity.ok(saved);
    }
}
