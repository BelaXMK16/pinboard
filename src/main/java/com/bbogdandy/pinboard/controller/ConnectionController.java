package com.bbogdandy.pinboard.controller;

import com.bbogdandy.pinboard.model.Connection;
import com.bbogdandy.pinboard.repository.ConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/connections")
public class ConnectionController {
    private final ConnectionRepository connectionRepository;

    public ConnectionController(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @PostMapping("/add")
    public Connection createConnection(@RequestBody Connection connection) {
        log.info("Adding connection {} {} {}", connection.getTo(), connection.getFrom(), connection.getColor());
        return connectionRepository.save(connection);
    }

    @GetMapping("/pin/{pinId}")
    public List<Connection> getConnectionsForPin(@PathVariable Long pinId) {
        log.info("Getting connections for pin {}", pinId);
        return connectionRepository.findByFromIdOrToId(pinId, pinId);
    }
}

