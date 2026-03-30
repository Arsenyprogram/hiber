package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.CourseDto;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.CourseLevel;
import org.example.onlinecourse.repository.CourseRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public void create(CourseDto dto) {

        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .durationInHours(1)
                .language("EN")
                .createdAt(LocalDate.now())
                .level(CourseLevel.BEGINNER)
                .build();

        courseRepository.save(course);
    }

    public void update(Long id, CourseDto dto) {

        Course course = courseRepository.findById(id).orElseThrow();

        course.setTitle(dto.getTitle());
        course.setPrice(dto.getPrice());

        courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}