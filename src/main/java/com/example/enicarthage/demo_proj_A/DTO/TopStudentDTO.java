package com.example.enicarthage.demo_proj_A.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopStudentDTO {
    private Long id;
    private String name;
    private String avatar;
    private double score;
    private Double  progress;
}