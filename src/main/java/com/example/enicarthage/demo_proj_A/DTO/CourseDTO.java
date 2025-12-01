package com.example.enicarthage.demo_proj_A.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String level;
    private String duration;
    private double rating;
    private int students;
}