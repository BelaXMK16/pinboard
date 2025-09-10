package com.bbogdandy.pinboard.controller;

import com.bbogdandy.pinboard.entity.dto.UserInfoDTO;
import com.bbogdandy.pinboard.entity.dto.UserInfoExtendedDTO;
import com.bbogdandy.pinboard.entity.request.AuthRequest;
import com.bbogdandy.pinboard.model.Completeable;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.model.enumerators.QuestType;
import com.bbogdandy.pinboard.service.JwtService;
import com.bbogdandy.pinboard.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserInfoService userInfoService;

    public record JwtResponse(String token) {}

    @PostMapping("/auth/addNewUser")
    public  ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {

        return ResponseEntity.ok(service.addUser(userInfo));
    }

    @PostMapping("/auth/generateToken")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(new JwtResponse(jwtService.generateToken(authRequest.getUsername())));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<UserInfoExtendedDTO> getUserDetails(@PathVariable String userId) {
        UserInfoExtendedDTO userInfoDTO = service.getUserProperties(Long.parseLong(userId));
        return ResponseEntity.ok(userInfoDTO);
    }
    @GetMapping("/quest/increment/{questType}")
    public ResponseEntity<List<Completeable>> incrementQuests(@PathVariable String questType) {
        List<Completeable> result = service.incrementQuests(QuestType.valueOf(questType));
        return ResponseEntity.ok(result);
    }

}