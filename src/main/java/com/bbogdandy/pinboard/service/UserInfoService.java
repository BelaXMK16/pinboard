package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.entity.UserInfo;
import com.bbogdandy.pinboard.repository.UserInfoRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Log
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<UserInfo> userInfo = repository.findByUsername(username);
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Convert UserInfo to UserDetails (UserInfoDetails)
        UserInfo user = userInfo.get();
        log.info(List.of(new SimpleGrantedAuthority(user.getRole())).toString());
        return new User(user.getEmail(), user.getPassword(),  List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    // Add any additional methods for registering or managing users
    public String addUser(UserInfo userInfo) {
        // Encrypt password before saving
        log.info(userInfo.toString());
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully!";
    }
}