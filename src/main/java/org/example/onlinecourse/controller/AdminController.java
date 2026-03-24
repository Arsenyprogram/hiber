package org.example.onlinecourse.controller;

import org.example.onlinecourse.dto.UserUpdateDto;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.CourseLevel;
import org.example.onlinecourse.service.CategoryService;
import org.example.onlinecourse.service.CourseService;
import org.example.onlinecourse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;
    private final CategoryService categoryService;

    public AdminController(UserService userService, CourseService courseService, CategoryService categoryService) {
        this.userService = userService;
        this.courseService = courseService;
        this.categoryService = categoryService;
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/{id}/block")
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/activate")
    public String activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserUpdateDto updateDto = new UserUpdateDto();
        var user = userService.getUserById(id);
        updateDto.setId(user.getId());
        updateDto.setFirstName(user.getFirstName());
        updateDto.setLastName(user.getLastName());
        updateDto.setAge(user.getAge());

        model.addAttribute("user", user);
        model.addAttribute("updateDto", updateDto);
        return "admin/edit-user";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UserUpdateDto updateDto) {
        userService.updateUser(updateDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/courses")
    public String manageCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/courses";
    }

    @GetMapping("/courses/create")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("levels", CourseLevel.values());
        return "admin/create-course";
    }

    @PostMapping("/courses/create")
    public String createCourse(@ModelAttribute Course course) {
        courseService.createCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/{id}/edit")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("levels", CourseLevel.values());
        return "admin/edit-course";
    }

    @PostMapping("/courses/update")
    public String updateCourse(@RequestParam Long id, @ModelAttribute Course course) {
        courseService.updateCourse(id, course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/categories")
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/categories/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/create-category";
    }

    @PostMapping("/categories/create")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin/edit-category";
    }

    @PostMapping("/categories/update")
    public String updateCategory(@RequestParam Long id, @ModelAttribute Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/admin/categories";
    }
}