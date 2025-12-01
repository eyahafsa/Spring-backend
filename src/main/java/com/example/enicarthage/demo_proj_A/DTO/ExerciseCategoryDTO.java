package com.example.enicarthage.demo_proj_A.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseCategoryDTO {
    private Long id;
    private String title;
    private String description;
    private String icon;
    private String color;
    private int completed;
    private int totalExercises;
}