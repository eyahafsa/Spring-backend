package com.example.enicarthage.demo_proj_A.DTO;

import jakarta.persistence.*;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCategoryDTO {
    private Long id;
    private String title;
    private String description;
    private String icon;
    private int progress;
    private int completedLessons;
    private int totalLessons;
}
