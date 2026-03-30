package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.CategoryDto;
import org.example.onlinecourse.dto.CourseDto;
import org.example.onlinecourse.dto.UserUpdateDto;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.CourseLevel;
import org.example.onlinecourse.model.UserStatus;
import org.example.onlinecourse.service.CategoryService;
import org.example.onlinecourse.service.CourseService;
import org.example.onlinecourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final UserService userService;



    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("course", new CourseDto());
        return "admin/courses";
    }

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute CourseDto dto) {
        courseService.create(dto);
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable Long id,
                               @ModelAttribute CourseDto dto) {
        courseService.update(id, dto);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return "redirect:/admin/courses";
    }



    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", new CategoryDto());
        return "admin/categories";
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute CategoryDto dto) {
        categoryService.create(dto);
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute CategoryDto dto) {
        categoryService.update(id, dto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam UserStatus status) {
        userService.updateUserStatus(id, status);
        return "redirect:/admin/users";
    }
}