package org.example.onlinecourse.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}
