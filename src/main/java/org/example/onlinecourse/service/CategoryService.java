package org.example.onlinecourse.service;

import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional
    public Category createCategory(Category category) {
        category.setCreatedAt(LocalDate.now());
        category.setIsActive(true);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setIconUrl(categoryDetails.getIconUrl());
        category.setIsActive(categoryDetails.getIsActive());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}