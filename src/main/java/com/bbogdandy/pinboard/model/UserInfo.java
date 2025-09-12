package com.bbogdandy.pinboard.model;

import com.bbogdandy.pinboard.model.enumerators.QuestType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
    private int credits;

    public UserInfo(){
        rerollQuests();
        initMilestones();
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pin> pins = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "user_quests",
            joinColumns = @JoinColumn(name = "user_id"), //a tulajdonos entitasra mutat
            inverseJoinColumns = @JoinColumn(name = "quest_id")// a cel entitasra mutat
    )
    private List<Completeable> quests = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "user_milestones",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "milestone_id")
    )
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

    private void initMilestones() {
        try (InputStream is = getClass().getResourceAsStream("/milestones.json")) {
            ObjectMapper mapper = new ObjectMapper();
            List<Completeable> templates = mapper.readValue(is, new TypeReference<>() {});
            templates.forEach(t -> {
                t.setProgress(0);
                milestones.add(t);
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize milestones", e);
        }
    }

    public void addPin(Pin pin) {
        pins.add(pin);
        pin.setOwner(this);
    }
    public void removePin(Pin pin) {
        pins.remove(pin);
        pin.setOwner(null);
    }
    public void addBoard(Board board) {
        boards.add(board);
        board.setOwner(this);
    }
    public void removeBoard(Board board) {
        boards.remove(board);
        board.setOwner(null);
    }
}