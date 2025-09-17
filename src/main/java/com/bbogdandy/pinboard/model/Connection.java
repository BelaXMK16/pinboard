package com.bbogdandy.pinboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Setter @Getter
    @ManyToOne
    @JoinColumn(name = "from_pin_id")
    private Pin from;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "to_pin_id")
    private Pin to;

    @Setter
    @Getter
    private String color;

}
