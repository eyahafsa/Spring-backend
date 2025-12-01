package com.example.enicarthage.demo_proj_A.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certification {

    @Id
    private Long id;
    private String type;
    private String score;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Student etudiant;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Student getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Student etudiant) {
        this.etudiant = etudiant;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
