package com.bbogdandy.pinboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "boards")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private boolean isPublic;
    @Setter
    private int width = 1123;
    @Setter                     // fekvo a4-es lap
    private int height = 794;
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
        pin.setBoard(this);
    }
    public void removePin(Pin pin) {
        pins.remove(pin);
        pin.setBoard(null);
    }

    public LocalDate getDate(){return null;}
    public void setStartDate(LocalDate date){};

    public String echoType(){
        return "normal";
    }

    public List<String> getColumnsAsList(){
        return new ArrayList<String>();
    }


}
