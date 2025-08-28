package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.NewBoardRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.service.BoardService;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/getAll")
    public List<Board> getAllBoards() {
        log.info("Getting all boards");
        return boardService.allBoards();
    }

    @PostMapping("/add")
    public ResponseEntity<Board> createBoard(@RequestBody NewBoardRequest request) {
        Board board = new Board();
        board.setName(request.getName());
        board.setPublic(request.isPublic());
        log.info("Creating Board: "+ board.toString());
        Board saved = boardService.saveBoard(board);
        return ResponseEntity.ok(saved);
    }
}
