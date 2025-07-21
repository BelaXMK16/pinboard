package com.bbogdandy.pinboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "boards")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private boolean isPublic;
    @ManyToOne
    private User user;
    @Override
    public String toString() {
        return "id "+id + "name: " + name + ", isPublic: " + isPublic + ", user: " + user;
    }
}
