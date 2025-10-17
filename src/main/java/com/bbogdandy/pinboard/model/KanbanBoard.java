package com.bbogdandy.pinboard.model;

import jakarta.persistence.Entity;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class KanbanBoard extends Board {
    @Setter
    String columns = "Tervben,Készül,Kész";

    public String getStage(Pin pin){
        int x = pin.getX();
        String[] stagesArray =  columns.split(",");
        int stageWidth = this.getWidth() / stagesArray.length;
        return stagesArray[x/stageWidth]; // SURELY lefele fog kerekiteni
    }
    @Override
    public List<String> getColumnsAsList(){
        String[] columnsArray =  columns.split(",");
        return columnsArray.length > 0 ? Arrays.asList(columnsArray) : new ArrayList<>();
    }
    @Override
    public String echoType(){
        return "kanban";
    }

}
