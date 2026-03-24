package org.example.onlinecourse.service;

import org.example.onlinecourse.dto.UserRegistrationDto;
import org.example.onlinecourse.dto.UserUpdateDto;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.model.UserRole;
import org.example.onlinecourse.model.UserStatus;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAge(registrationDto.getAge());
        user.setRegistrationDate(LocalDate.now());
        user.setRole(UserRole.STUDENT);
        user.setStatus(UserStatus.ACTIVE);
        user.setConfirmed("CONFIRMED");

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User updateUser(UserUpdateDto updateDto) {
        User user = getUserById(updateDto.getId());
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setAge(updateDto.getAge());
        return userRepository.save(user);
    }

    @Transactional
    public void blockUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.BLOCKED);
        user.setConfirmed("BLOCKED");
        userRepository.save(user);
    }

    @Transactional
    public void activateUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setConfirmed("CONFIRMED");
        userRepository.save(user);
    }
}