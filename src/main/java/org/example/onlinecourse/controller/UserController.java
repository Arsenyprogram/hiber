package org.example.onlinecourse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome!");
        return "home";
    }

    @PostMapping("/home")
    public String post(@RequestParam String data, Model model) {
        model.addAttribute("message", "You sent: " + data);
        return "home";
    }
}