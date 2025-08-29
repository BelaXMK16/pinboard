package com.bbogdandy.pinboard.auth;

import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserInfoRepository userRepository;
    private PasswordEncoder passwordEncoder; // not final

    public AdminUserInitializer(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminUsername = "adminUser1";
        String adminPassword = "admin123";
        String adminRole = "ROLE_ADMIN";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            UserInfo admin = new UserInfo();
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole("ROLE_ADMIN");
            admin.setEmail("test@email.test");
            admin.setUsername(adminUsername);
            userRepository.save(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
