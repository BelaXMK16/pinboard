package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.NewBoardRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.repository.UserInfoRepository;
import com.bbogdandy.pinboard.service.BoardService;

import com.bbogdandy.pinboard.service.UserInfoService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<Board> getAllBoards() {
        log.info("Getting all boards");
        return boardService.allBoards();
    }

    @PostMapping("/add")
    public ResponseEntity<Board> createBoard(@RequestBody NewBoardRequest request) {
        Board board = new Board();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserInfo user = userInfoService.getSimpleUserInfo(username);

        board.setPublic(request.isPublic());
        board.setOwner(user);
        board.setTitle(request.getTitle());
        board.setDescription(request.getDescription());

        log.info("Creating Board with parameters: "+ board);
        Board saved = boardService.saveBoard(board);

        return ResponseEntity.ok(saved);
    }

}
