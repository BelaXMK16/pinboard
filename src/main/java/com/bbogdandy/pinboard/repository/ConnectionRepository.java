package com.bbogdandy.pinboard.repository;

import com.bbogdandy.pinboard.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByFromIdOrToId(Long fromId, Long toId);
}
