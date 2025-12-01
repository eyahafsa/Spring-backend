package com.example.enicarthage.demo_proj_A.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class StudentScoresDTO {
    private Long studentId;
    private String studentName;
    private Double delfScore;       // Score DELF (Français)
    private Double toeicScore;      // Score TOEIC (Anglais)
    private Double currentFrench;   // Note actuelle en français
    private Double currentEnglish;  // Note actuelle en anglais

    // Méthode utilitaire pour formater les scores
    public String getFormattedDelfScore() {
        return delfScore != null ? String.format("%.0f/100", delfScore) : "N/A";
    }

    public String getFormattedToeicScore() {
        return toeicScore != null ? String.format("%.0f/990", toeicScore) : "N/A";
    }

    public String getFormattedCurrentFrench() {
        return currentFrench != null ? String.format("%.1f/20", currentFrench) : "N/A";
    }

    public String getFormattedCurrentEnglish() {
        return currentEnglish != null ? String.format("%.1f/20", currentEnglish) : "N/A";
    }
}