package com.bbogdandy.pinboard.model;

import jakarta.persistence.Entity;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
public class CalendarBoard extends Board {
    LocalDate startDate; // amugy jo lenne ha nem felejtenem el neki az adott honap elso napjat megadni

    public void setStartDate(LocalDate date) {
        this.startDate=date.withDayOfMonth(1);
    }

    public int dayOffset(){
        //az eltolódást adjuk meg egy olyan naptárhoz amely táblázatszerűen van megadva, és oszloponként ugyanaz a nap van.
        int dayOfWeekNumerical = startDate.getDayOfWeek().getValue();
        return dayOfWeekNumerical-1; //ha hetfo akkor 0 mert az elso elem hetfo lesz a tablazatban
    }

    public static int getDayNumberNew(LocalDate date) {
        //hetfo - 1
        //vasarnap - 7
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }
    public int getDateFromPinPosition(Pin pin){
        int x = pin.getX();
        int y = pin.getY();
        int cellWidth = getWidth() / 7; //leosztja hogy milyen szeles egy cella
        int cellHeight = getHeight() / 6; //lesoztja hogy milyen magas egy cella
        //IGEN MINT KIDERULT NEHANY HONAP LEHET HAT CELLA MAGAS
        int cellPositionX = x / cellWidth; // kiszamolja hanyadik cellaban van az adott tu
        int cellPositionY = y / cellHeight;// kiszamolja hanyadik oszlopban van az adott tu
        return cellPositionX + (cellPositionY)*7 - dayOffset(); //a honap n.-edik napjat kapjuk vissza
    }
}
