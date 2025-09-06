package com.bbogdandy.pinboard.model;

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

    @OneToMany
    private List<Pin> pins = new ArrayList<>();
    @OneToMany
    private List<Board> boards  = new ArrayList<>();

    @OneToMany
    private List<UserInfo> invites  = new ArrayList<>();

}