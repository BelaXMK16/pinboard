package com.bbogdandy.pinboard.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.awt.*;

@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "from_pin_id")
    private Pin from;

    @Getter
    @ManyToOne
    @JoinColumn(name = "to_pin_id")
    private Pin to;

    @Getter
    String color;

}
