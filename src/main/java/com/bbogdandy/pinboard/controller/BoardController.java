package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.dto.BoardInfoExtendedDto;
import com.bbogdandy.pinboard.entity.request.NewBoardRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.service.BoardService;

import com.bbogdandy.pinboard.service.UserInfoService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final UserInfoService userInfoService;
    private final BoardService boardService;

    public BoardController(BoardService boardService, UserInfoService userInfoService) {
        this.boardService = boardService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/getAll")
    public List<BoardInfoExtendedDto> getAllBoards() {
        log.info("Getting all boards");
        return boardService.allBoards();
    }

    @PostMapping("/add")
    public ResponseEntity<BoardInfoExtendedDto> createBoard(@RequestBody NewBoardRequest request) {
        Board board = new Board();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserInfo user = userInfoService.getSimpleUserInfo(username);

        board.setPublic(request.isPublic());
        board.setOwner(user);
        board.setTitle(request.getTitle());
        board.setDescription(request.getDescription());
        log.info("Creating Board with parameters: "+ board);
        //TODO: ezt át kell rakni a service-be
        BoardInfoExtendedDto saved = new BoardInfoExtendedDto(boardService.saveBoard(board));

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
