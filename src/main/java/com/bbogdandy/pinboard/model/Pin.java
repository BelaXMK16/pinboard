package com.bbogdandy.pinboard.model;

import com.bbogdandy.pinboard.types.PinType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "pins")
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo owner;
    private int x;
    private int y;
    private String content;
    private PinType pinType;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
