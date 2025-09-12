package com.bbogdandy.pinboard.model;

import com.bbogdandy.pinboard.model.enumerators.QuestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
    private int credits;

    @OneToMany
    private List<Pin> pins = new ArrayList<>();
    @OneToMany
    private List<Board> boards  = new ArrayList<>();

    @OneToMany
    private List<UserInfo> invites  = new ArrayList<>();
    @OneToMany
    private List<Completeable> quests  = new ArrayList<>();
    @OneToMany
    private List<Completeable> milestones = new ArrayList<>();


    public void rerollQuests(){
        quests.clear();
        for(int i = 0; i < 3; i++){
            rollNewQuest();
        }
    }

    public void cashOutQuest(Completeable quest){
        this.quests.remove(quest);
        this.credits += quest.getReward();
        rollNewQuest();
    }

    public void rollNewQuest(){
        quests.add(new Completeable());
    }

    public List<Completeable> triggerQuests(QuestType questType){
        List<Completeable> completedList = new ArrayList<>();
        for(Completeable quest : quests){//atnezzuk az osszes questet
            if(questType == quest.getQuestType()){ //ha az adott questType-pal rendelkezik a quest
                boolean justCompleted = quest.incrementProgress(); //ez azoknak a questeknek a listaja amit EPPEN MOST teljesitettunk.
                //az elkeszult questek helyere masik questeket guritunk
                //
                if(justCompleted){
                    completedList.add(quest);
                    quests.remove(quest);
                    cashOutQuest(quest);
                }
            }
        }
        return  completedList;
    }

}