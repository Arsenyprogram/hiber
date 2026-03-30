package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.CategoryDto;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> CategoryDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .description(c.getDescription())
                        .iconUrl(c.getIconUrl())
                        .isActive(c.getIsActive())
                        .build()
                ).toList();
    }

    public void create(CategoryDto dto) {

        Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .iconUrl(dto.getIconUrl())
                .isActive(true)
                .createdAt(LocalDate.now())
                .build();

        categoryRepository.save(category);
    }

    public void update(Long id, CategoryDto dto) {

        Category category = categoryRepository.findById(id).orElseThrow();

        category.setName(dto.getName()); // UPDATE ✔
        category.setDescription(dto.getDescription());

        categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}