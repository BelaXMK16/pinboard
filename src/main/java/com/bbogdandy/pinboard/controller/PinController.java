package com.bbogdandy.pinboard.controller;


import com.bbogdandy.pinboard.entity.dto.PinDTO;
import com.bbogdandy.pinboard.entity.request.PinConnectionRequest;
import com.bbogdandy.pinboard.entity.request.PinRequest;
import com.bbogdandy.pinboard.model.Connection;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.repository.ConnectionRepository;
import com.bbogdandy.pinboard.repository.PinRepository;
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
    private final PinRepository pinRepository;
    private final ConnectionRepository connectionRepository;
    public record PinResponse(long id, int x, int y,String content) {}
    public record ConnectionResponse(long id, PinResponse from, PinResponse to,String color) {}

    public PinController(BoardService boardService, PinService pinService, PinRepository pinRepository, ConnectionRepository connectionRepository) {
        this.boardService = boardService;
        this.pinService = pinService;
        this.pinRepository = pinRepository;
        this.connectionRepository = connectionRepository;
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePin(@PathVariable Long id){
        pinService.deletePin(id);
        return ResponseEntity.ok("A tű törlésre került.");

    }

    @PostMapping("/connections/add")
    public ResponseEntity<ConnectionResponse> addConnection(@RequestBody PinConnectionRequest request) {
        Pin fromPin = pinRepository.findById(request.getFromId())
                .orElseThrow(() -> new RuntimeException("From Pin not found"));
        Pin toPin = pinRepository.findById(request.getToId())
                .orElseThrow(() -> new RuntimeException("To Pin not found"));

        Connection conn = new Connection();
        conn.setFrom(fromPin);
        conn.setTo(toPin);
        conn.setColor(request.getColor());

        conn = connectionRepository.save(conn);
         ConnectionResponse result = new ConnectionResponse(
                 conn.getId(),
                 new PinResponse(conn.getFrom().getId(), conn.getFrom().getX(),conn.getFrom().getY(),conn.getFrom().getContent()),
                 new PinResponse(conn.getTo().getId(), conn.getTo().getX(),conn.getTo().getY(), conn.getTo().getContent()),
                 conn.getColor()
         );
         return ResponseEntity.ok(result);
    }
}

