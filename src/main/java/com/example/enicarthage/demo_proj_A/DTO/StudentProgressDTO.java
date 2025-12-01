package com.example.enicarthage.demo_proj_A.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProgressDTO {
    private int exercisesCompleted;
    private double averageScore;
    private String timePracticed;
}