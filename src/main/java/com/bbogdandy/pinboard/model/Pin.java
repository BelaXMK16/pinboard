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
    private UserInfo user;

    private int x;
    private int y;
    private String content;
    private PinType pinType;

    @ManyToOne
    private Board board;

}
