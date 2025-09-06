package com.bbogdandy.pinboard.repository;

import com.bbogdandy.pinboard.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin,Long> {

    List<Pin> findAll();
    Pin save(Pin pin);
    Pin findById(long id);
}
