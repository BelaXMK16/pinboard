package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.dto.BoardInfoExtendedDto;
import com.bbogdandy.pinboard.entity.request.NewBoardRequest;
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
    public List<BoardInfoExtendedDto> getAllBoards() {
        log.info("Getting all boards");
        return boardService.allBoards();
    }

    @PostMapping("/add")
    public ResponseEntity<BoardInfoExtendedDto> createBoard(@RequestBody NewBoardRequest request) {
        BoardInfoExtendedDto saved = new BoardInfoExtendedDto(boardService.addNewBoard(request));
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BoardInfoExtendedDto> getBoards(@PathVariable String id) {
        return ResponseEntity.ok(
                new BoardInfoExtendedDto(
                        boardService.findBoardById(Long.parseLong(id))
                )
        );
    }

}
