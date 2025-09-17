package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.entity.request.PinRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.repository.PinRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Log
public class PinService {
    @Autowired
    BoardService boardService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    PinRepository pinRepository;

    public Pin addPin(PinRequest pinRequest) {
        Pin result = new Pin();
        log.info(pinRequest.toString());
        Board targetBoard = boardService.findBoardById(pinRequest.getBoardId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserInfo user = userInfoService.getSimpleUserInfo(username);
        result.setOwner(user);
        result.setBoard(targetBoard);
        result.setX(pinRequest.getX());
        result.setY(pinRequest.getY());
        result.setContent(pinRequest.getContent());
        targetBoard.addPin(result);
        user.addPin(result);
        return pinRepository.save(result);
    }

    public Pin editPin(PinRequest pinRequest) {
         Pin toEdit = pinRepository.findById(pinRequest.id);
         toEdit.setX(pinRequest.getX());
         toEdit.setY(pinRequest.getY());
         toEdit.setContent(pinRequest.getContent());
         pinRepository.save(toEdit);
         return toEdit;

    }

    public void deletePin(Long pinId) {
        pinRepository.deleteById(pinId);
    }
}
