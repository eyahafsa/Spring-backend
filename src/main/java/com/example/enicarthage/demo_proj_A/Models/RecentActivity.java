package com.example.enicarthage.demo_proj_A.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseTitle;
    private String category;
    private LocalDate date;
    private int score;
    private String timeSpent;
}