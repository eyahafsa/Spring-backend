package com.example.enicarthage.demo_proj_A.Models;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String difficulty;
    private int duration;
    private int questions;
    private boolean completed;
    private Integer bestScore;

    @ManyToOne
    @ToString.Exclude
    private ExerciseCategory category;
}