package com.example.enicarthage.demo_proj_A.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String note;
    private String date;
    private String langue;
    private String niveau;
    private String semestre;
    private double seuilValidation;


    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Student etudiant;

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Teacher professeur;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
