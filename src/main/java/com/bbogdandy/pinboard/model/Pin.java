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
    private User user;

    private int position;
    private String Content;
    private PinType pinType;

    @ManyToOne
    private Board board;

}
