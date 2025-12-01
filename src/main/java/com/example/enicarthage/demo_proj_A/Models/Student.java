package com.example.enicarthage.demo_proj_A.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String annee;
    private String email;
    @Column(name = "mot_de_passe")
    private String motDePasse;
    @ManyToOne
    @JoinColumn(name = "classe_id")
    private SchoolClass classe;

    @ManyToOne
    @JoinColumn(name = "specialite_id")
    private Speciality specialite;

    @OneToMany(mappedBy = "etudiant")
    private List<Note> notes;

    @OneToMany(mappedBy = "etudiant")
    private List<Certification> certifications;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SchoolClass getClasse() {
        return classe;
    }

    public void setClasse(SchoolClass classe) {
        this.classe = classe;
    }

    public Speciality getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Speciality specialite) {
        this.specialite = specialite;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }
}
