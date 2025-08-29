package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.entity.PinRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.model.UserInfo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Pin addPin(PinRequest pinRequest) {
        Pin result = new Pin();
        log.info(pinRequest.toString());
        Board targetBoard = boardService.findBoardById(pinRequest.getBoardId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserInfo user = userInfoService.getSimpleUserInfo(username);

        result.setUser(user);
        result.setBoard(targetBoard);
        result.setX(pinRequest.getX());
        result.setY(pinRequest.getY());
        result.setContent(pinRequest.getContent());
        targetBoard.addPin(result);
        //TODO: A PINHEZ HOZZAADNI HOGY KI A TULAJDONOSA
        return result;
    }
}
