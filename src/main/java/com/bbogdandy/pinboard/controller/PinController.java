package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.PinRequest;
import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.service.BoardService;
import com.bbogdandy.pinboard.service.PinService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/api/pins")
public class PinController {
    private final BoardService boardService;
    private final PinService pinService;

    public PinController(BoardService boardService,PinService pinService) {
        this.boardService = boardService;
        this.pinService = pinService;
    }

    @PostMapping("/add")
    public Pin addPin(@RequestBody PinRequest pinRequest) {
        Pin result = pinService.addPin(pinRequest);
        return result;

    }
}
