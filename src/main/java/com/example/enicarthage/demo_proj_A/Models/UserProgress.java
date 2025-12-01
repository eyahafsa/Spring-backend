package com.example.enicarthage.demo_proj_A.Models;


import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private Student user;

    private int exercisesCompleted;
    private double averageScore;
    private String timePracticed;
}
