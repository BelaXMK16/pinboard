package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.entity.dto.BoardInfoExtendedDto;
import com.bbogdandy.pinboard.entity.request.NewBoardRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.CalendarBoard;
import com.bbogdandy.pinboard.model.KanbanBoard;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserInfoService userInfoService;

    public BoardService(BoardRepository boardRepository, UserInfoService userInfoService) {
        this.boardRepository = boardRepository;
        this.userInfoService = userInfoService;
    }

    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    public List<BoardInfoExtendedDto> allBoards() {

        List<Board> boards =  boardRepository.findAll();
        List<BoardInfoExtendedDto> boardDtos = new ArrayList<>();
        for(Board board : boards){
            boardDtos.add(new BoardInfoExtendedDto(board));
        }
        return boardDtos;
    }

    public Board findBoardById(long id) {
        return boardRepository.findById(id);
    }
    public Board addNewBoard(NewBoardRequest request) {
        Board board;

        switch (request.getType()) {
            case "calendar":
                CalendarBoard calendarBoard = new CalendarBoard();
                calendarBoard.setStartDate(LocalDate.parse(request.getStartingDate()));
                board = calendarBoard;
                break;
            case "kanban":
                KanbanBoard kanbanBoard = new KanbanBoard();
                board = kanbanBoard;
                break;
            default:
                board = new Board();
                break;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserInfo user = userInfoService.getSimpleUserInfo(username);

        board.setPublic(request.isPublic());
        board.setOwner(user);
        board.setTitle(request.getTitle());
        board.setDescription(request.getDescription());

        log.debug("Creating Board with parameters: " + board);

        // TODO: save to repository if needed
        return board;
    }

}

