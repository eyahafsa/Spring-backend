package com.example.enicarthage.demo_proj_A.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeHistoryDTO {
    private String semester;
    private String subject;
    private LocalDate date;
    private String grade;
    private int coefficient;
}