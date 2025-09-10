package com.bbogdandy.pinboard.model;

import com.bbogdandy.pinboard.model.enumerators.QuestType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Entity
@AllArgsConstructor
public class Completeable {
    @Id
    private long id;
    private QuestType questType;
    private String name;
    private String description;
    private int goal;
    private int reward;
    private int progress;

    public Completeable() {
        this.progress = 0;
        this.reward = 100;
        this.questType = QuestType.values()[new Random().nextInt(QuestType.values().length)];
        Random rand = new Random();
        switch (questType) {
            case EDIT_PIN:
                this.goal=rand.nextInt(5);
                this.name="Módosíts Tűket!";
                this.description="Mindig történik valami a nagyvilágban! A változatosság kedvéért legyél te aki változtat! Módosíts "+goal+" tűt hogy elérd ezt a célod" ;
                break;
            case LOOK_AT_PIN:
                this.goal=rand.nextInt(10);
                this.name = "Vizsgálj Meg Tűket!";
                this.description = "Az élet mindig valami újdonságot hoz. Persze, erről az ujdonságról nem mindig tudhatsz, de lehet hogy valamelyik barátod megosztotta! Vizsgálj meg " + goal + " tűt hogy valami újat láss! ";
                break;
            case MAKE_PLAN:
                this.goal=rand.nextInt(3);
                this.name = "Tervezz!";
                this.description = "A jó dolgok spontán is történhetnek, de nem esik egy kicsit jobban ha te tervezed meg őket? Ossz meg 3 új tervet a barátaiddal.";
                break;
            case GET_FRIENDS:
                this.goal=1;
                this.name = "Szocialízálódj!";
                this.description = "Szerinted is jobb az élet több baráttal? Gyerünk jelölj be valakit!";
                break;
            case ADD_PIN:
                this.goal=rand.nextInt(5);
                this.name = "Új tűk!";
                this.description = "Mindig történik valami a nagyvilágban! A változatosság kedvéért legyél te az aki megosztja másokkal! Hozz létre "+goal+" tűt hogy elérd ezt a célod";
                break;


        }

    }

    public boolean incrementProgress(){
        //ha a küldetés még folyamatban van akkor pötyögtesse egyel feljebb a progresst
        if(this.progress < this.goal){
            this.progress++;
            return progress==goal; //elkészült a quest, itt ha pont most lett kesz valószínűleg hozzáadjuk egy lstához amiben értesítjük a felhasználót
        }
        return false; // vagy kész, és már elkészült a küldetés, vagy még nem, de lényeg a lényeg nem adjuk hozzá az értesítések listájához
    }



}
