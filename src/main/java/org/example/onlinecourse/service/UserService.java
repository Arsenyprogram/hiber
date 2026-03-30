package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.UserDto;
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
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegistrationDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .age(dto.getAge())
                .registrationDate(LocalDate.now())
                .role(UserRole.STUDENT)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> UserDto.builder()
                        .id(u.getId())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .email(u.getEmail())
                        .age(u.getAge())
                        .role(u.getRole())
                        .status(u.getStatus())
                        .build()
                ).toList();
    }

    public void updateUserStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(status);
        userRepository.save(user);
    }
}