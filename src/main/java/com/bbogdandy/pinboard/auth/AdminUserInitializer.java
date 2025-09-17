package com.bbogdandy.pinboard.auth;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.model.UserInfo;
import com.bbogdandy.pinboard.repository.BoardRepository;
import com.bbogdandy.pinboard.repository.PinRepository;
import com.bbogdandy.pinboard.repository.UserInfoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserInfoRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PinRepository pinRepository;

    public AdminUserInitializer(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        String adminUsername = "adminUser1";
        String adminPassword = "admin123";
        String adminRole = "ROLE_ADMIN";
        String adminEmail = "test@email.test";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            UserInfo admin = new UserInfo();
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(adminRole);
            admin.setEmail(adminEmail);
            admin.setUsername(adminUsername);
            userRepository.save(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }

        String testUsername = "testUser1";
        String testPassword = "test123";
        String testRole = "ROLE_USER";
        String testemail = "testuser@email.test";

        if (userRepository.findByUsername(testUsername).isEmpty()) {
            UserInfo user = new UserInfo();
            user.setPassword(passwordEncoder.encode(testPassword));
            user.setRole(testRole);
            user.setEmail(testemail);
            user.setUsername(testUsername);
            userRepository.save(user);
            System.out.println("Test user created.");
        } else {
            System.out.println("Test user already exists.");
        }
        UserInfo user = userRepository.findByUsername(testUsername).get();
        if (user.getBoards().isEmpty()) {
            Board publicBoard = new Board();
            publicBoard.setPublic(true);
            publicBoard.setOwner(user);
            publicBoard.setTitle("Public Board Iam a public boadr!");
            publicBoard.setDescription("this is a public testing board");

            Pin publicPin = new Pin();
            publicPin.setBoard(publicBoard);
            publicPin.setOwner(user);
            publicPin.setY(1);
            publicPin.setX(1);
            publicPin.setContent("this is a pin");

            publicBoard.addPin(publicPin);
            user.getBoards().add(publicBoard);
            user.getPins().add(publicPin);

            boardRepository.save(publicBoard);

            Board privateBoard = new Board();
            privateBoard.setPublic(false);
            privateBoard.setOwner(user);
            privateBoard.setTitle("HEY IM PRIVATE");
            privateBoard.setDescription("this is a private testing board");

            Pin privatePin = new Pin();
            privatePin.setBoard(privateBoard);
            privatePin.setOwner(user);
            privatePin.setY(11);
            privatePin.setX(11);
            privatePin.setContent("this is a pin");

            privateBoard.addPin(privatePin);
            user.getBoards().add(privateBoard);
            user.getPins().add(privatePin);

            boardRepository.save(privateBoard);
            userRepository.save(user);
        }



    }
}
