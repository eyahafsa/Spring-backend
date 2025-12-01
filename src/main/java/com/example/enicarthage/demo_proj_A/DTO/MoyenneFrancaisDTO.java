package com.example.enicarthage.demo_proj_A.DTO;

public class MoyenneFrancaisDTO {
    private String semestre;
    private double moyenne;

    // Constructeurs
    public MoyenneFrancaisDTO(String semestre, double moyenne) {
        this.semestre = semestre;
        this.moyenne = moyenne;
    }

    // Getters et Setters
    public String getSemestre() {
        return semestre;
    }

    public double getMoyenne() {
        return moyenne;
    }

    // ... setters si nécessaire
}