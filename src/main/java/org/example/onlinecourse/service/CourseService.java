package org.example.onlinecourse.service;

import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Transactional
    public Course createCourse(Course course) {
        course.setCreatedAt(LocalDate.now());
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setPrice(courseDetails.getPrice());
        course.setDurationInHours(courseDetails.getDurationInHours());
        course.setLanguage(courseDetails.getLanguage());
        course.setLevel(courseDetails.getLevel());
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}