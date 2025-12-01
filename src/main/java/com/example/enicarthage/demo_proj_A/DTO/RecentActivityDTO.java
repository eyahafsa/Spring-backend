package com.example.enicarthage.demo_proj_A.DTO;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentActivityDTO {
    private Long id;
    private String exerciseTitle;
    private String category;
    private String date;
    private int score;
    private String timeSpent;
}