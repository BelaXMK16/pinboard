package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.entity.dto.UserInfoDTO;
import com.bbogdandy.pinboard.entity.dto.UserInfoExtendedDTO;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.repository.UserInfoRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByEmail(email); //email alapjan keressuk a usert -- ezt nem tudtam mashogy megoldani hogy kell username alapjan parseolni????
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        UserInfo user = userInfo.get();
        log.info(List.of(new SimpleGrantedAuthority(user.getRole())).toString());
        return new User(user.getEmail(), user.getPassword(),  List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    public UserInfo getSimpleUserInfo(String email) {
        Optional<UserInfo> userInfo = repository.findByEmail(email);
        return userInfo.get();
    }

    public String addUser(UserInfo userInfo) {
        log.info(userInfo.toString());
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully!";
    }

    public UserInfoExtendedDTO getUserProperties(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long askerId = repository.findByEmail(auth.getName()).get().getId();
        String email = repository.findById(id).get().getEmail();
        UserInfo user = getSimpleUserInfo(email);
        return new UserInfoExtendedDTO(user, askerId);
    }
}