package com.bbogdandy.pinboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private boolean isPublic;
    @Setter
    private String title;
    @Setter
    private String description;
    @ManyToOne
    private UserInfo owner;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Pin> pins = new ArrayList<>();
    @Override
    public String toString() {
        return "id "+id + ", isPublic: " + isPublic + ", user: " + owner;
    }
    public void addPin(Pin pin){
        pins.add(pin);
    }
}
