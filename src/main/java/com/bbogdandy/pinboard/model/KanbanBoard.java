package com.bbogdandy.pinboard.model;

import jakarta.persistence.Entity;
import lombok.Setter;
@Entity
public class KanbanBoard extends Board {
    @Setter
    String stages = "Tervben,Készül,Kész";

    public String getStage(Pin pin){
        int x = pin.getX();
        String[] stagesArray =  stages.split(",");
        int stageWidth = this.getWidth() / stagesArray.length;
        return stagesArray[x/stageWidth]; // SURELY lefele fog kerekiteni
    }

}
