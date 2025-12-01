package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.DTO.StudentDTO;
import com.example.enicarthage.demo_proj_A.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByEmailAndMotDePasse(String email, String motDePasse);
    @Query("SELECT s FROM Student s WHERE s.annee = :annee OR s.annee = SUBSTRING(:annee, 1, 4)")
    List<Student> findByAnnee(String annee);

    List<Student> findByClasse_Nom(String className);

    @Query("SELECT s FROM Student s WHERE (s.annee = :annee OR s.annee = SUBSTRING(:annee, 1, 4)) AND s.specialite.nom = :specialiteNom")
    List<Student> findByAnneeAndSpecialiteNom(String annee, String specialiteNom);

    @Query("SELECT s FROM Student s WHERE (s.annee = :annee OR s.annee = SUBSTRING(:annee, 1, 4)) AND s.classe.nom = :classeNom")
    List<Student> findByAnneeAndClasseNom(String annee, String classeNom);

    @Query("SELECT DISTINCT s.annee FROM Student s WHERE s.annee IS NOT NULL ORDER BY s.annee DESC")
    List<String> findDistinctYears();

    @Query("SELECT s FROM Student s JOIN FETCH s.notes n JOIN FETCH n.module m WHERE s.id = :studentId")
    Student findByIdWithNotesAndModules(Long studentId);

    @Query("SELECT DISTINCT s.classe.nom FROM Student s WHERE s.specialite.nom = :filiere")
    List<String> findClassesByFiliere(String filiere);

    @Query("SELECT s FROM Student s WHERE s.specialite.nom = :filiere AND s.classe.nom = :classe")
    List<Student> findByFiliereAndClasse(String filiere, String classe);

    Optional<Student> findByEmail(String email);

    @Query("SELECT DISTINCT c.nom FROM SchoolClass c WHERE c.filiere.nom = :filiere AND c.professeur.id = :professeurId")
    List<String> findClassesByFiliereAndProfesseur(String filiere, Long professeurId);
}
