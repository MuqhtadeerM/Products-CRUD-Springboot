package com.example.CRUD_Springboot.config;

import com.example.CRUD_Springboot.entity.User;
import com.example.CRUD_Springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        seedUser("admin", "admin123", "ADMIN");
        seedUser("user", "user123", "USER");
    }

    private void seedUser(String username, String rawPassword, String role) {

        userRepository.findByUsername(username).ifPresentOrElse(
                existingUser -> {
                    // Always re-encode on startup so a bad/manual DB edit
                    // (plain text, wrong hash, wrong length, etc.) self-heals.
                    existingUser.setPassword(passwordEncoder.encode(rawPassword));
                    existingUser.setRole(role);
                    userRepository.save(existingUser);
                    System.out.println("Re-synced user: " + username);
                },
                () -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(passwordEncoder.encode(rawPassword));
                    newUser.setRole(role);
                    userRepository.save(newUser);
                    System.out.println("Created user: " + username);
                }
        );
    }
}