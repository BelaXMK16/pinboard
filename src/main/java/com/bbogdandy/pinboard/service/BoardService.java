package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.entity.dto.BoardInfoExtendedDto;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
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
}

