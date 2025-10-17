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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PinDTO> updatePinPosition(
            @PathVariable Long id,
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            @RequestParam("color") String color,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        PinRequest request = new PinRequest();
        request.setId(id);
        request.setX(x);
        request.setY(y);
        if(color!=null) request.setPinColor(color);
        else request.setPinColor("black");

        request.setContent(content);

        if (file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            request.setContent(content + fileName);
            log.info(fileName);
        }

        log.info("Pin Edited: " + request.toString());

        Pin result = pinService.editPin(request);
        return ResponseEntity.ok(new PinDTO(result));
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
        if(conn.getFrom().getId() == conn.getTo().getId()) return ResponseEntity.ok().build();
        ConnectionResponse result = new ConnectionResponse(
                conn.getId(),
                new PinResponse(conn.getFrom().getId(), conn.getFrom().getX(),conn.getFrom().getY(),conn.getFrom().getContent()),
                new PinResponse(conn.getTo().getId(), conn.getTo().getX(),conn.getTo().getY(), conn.getTo().getContent()),
                conn.getColor()
        );
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/connections/{id}")
    public ResponseEntity<String> deleteConnection(@PathVariable Long id) {

            connectionRepository.deleteById(id);
            return ResponseEntity.ok("Connection deleted.");
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadsDir = "uploads/";
        Path uploadsPath = Paths.get(uploadsDir);
        if (!Files.exists(uploadsPath)) {
            Files.createDirectories(uploadsPath);
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = uploadsPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName;
    }

}

