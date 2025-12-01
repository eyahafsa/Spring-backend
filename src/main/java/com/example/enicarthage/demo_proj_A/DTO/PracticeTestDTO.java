package com.example.enicarthage.demo_proj_A.DTO;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeTestDTO {
    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private int duration;
    private int questions;
    private boolean completed;
    private Integer bestScore;
}