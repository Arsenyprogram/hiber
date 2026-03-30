package org.example.onlinecourse.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
}
