package com.example.enicarthage.demo_proj_A.Models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String imageUrl;
    private String level;
    private String duration;
    private double rating;
    private int students;
    private boolean featured;

    @ManyToOne
    @ToString.Exclude
    private CourseCategory category;
}