package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.UserRegistrationDto;
import org.example.onlinecourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/signIn")
    public String login() {
        return "login";
    }

    @GetMapping("/signUp")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/signUp")
    public String register(@ModelAttribute("user") UserRegistrationDto dto,
                           Model model) {

        try {
            userService.register(dto);
            return "redirect:/signIn";

        } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }
}