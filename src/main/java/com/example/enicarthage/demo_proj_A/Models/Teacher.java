package com.example.enicarthage.demo_proj_A.Models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    @OneToMany(mappedBy = "professeur")
    private List<SchoolClass> classes;

    @OneToMany(mappedBy = "professeur")
    private List<Note> notes;

    @ManyToMany
    @JoinTable(
            name = "professeur_module",
            joinColumns = @JoinColumn(name = "professeur_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules;
}

