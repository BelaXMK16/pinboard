package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.dto.PinDTO;
import com.bbogdandy.pinboard.entity.request.PinRequest;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.service.BoardService;
import com.bbogdandy.pinboard.service.PinService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PinDTO> addPin(@RequestBody PinRequest pinRequest) {
        Pin result = pinService.addPin(pinRequest);

        return ResponseEntity.ok(new PinDTO(result));

    }
    @PutMapping("/{id}")
    public ResponseEntity<Pin> updatePinPosition(
            @PathVariable Long id,
            @RequestBody PinRequest request) {
            request.setId(id);
            log.info("Pin Edited: " + request.toString());
            Pin result = pinService.editPin(request);

            return ResponseEntity.ok(result);

    }

}

