package com.example.enicarthage.demo_proj_A.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationStatsDTO {
    private long dlefB2Count;
    private long toeicB2Count;
    private long englishValidations;
    private long frenchValidations;
}