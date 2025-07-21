package com.bbogdandy.pinboard.service;

import com.bbogdandy.pinboard.model.User;
import com.bbogdandy.pinboard.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("User not found") {
                    @Override
                    public Authentication getAuthenticationRequest() {
                        return super.getAuthenticationRequest();
                    }
                });
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
