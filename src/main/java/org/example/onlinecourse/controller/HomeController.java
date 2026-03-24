package org.example.onlinecourse.controller;

import org.example.onlinecourse.dto.UserUpdateDto;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/home/profile")
    public String getProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("updateDto", new UserUpdateDto());
        return "profile";
    }

    @PostMapping("/home/profile/update")
    public String updateProfile(@ModelAttribute UserUpdateDto updateDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (currentUser != null) {
            updateDto.setId(currentUser.getId());
            userService.updateUser(updateDto);
        }

        return "redirect:/home/profile?updated";
    }
}