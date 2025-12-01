package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.Models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("SELECT n.note FROM Note n WHERE n.etudiant.id = :studentId AND n.langue = 'FRANCAIS' ORDER BY n.date DESC LIMIT 1")
    Double findLatestFrenchGradeByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT n.note FROM Note n WHERE n.etudiant.id = :studentId AND n.langue = 'ANGLAIS' ORDER BY n.date DESC LIMIT 1")
    Double findLatestEnglishGradeByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.module.id = :moduleId")
    Note findByEtudiantIdAndModuleId(Long etudiantId, Long moduleId);

    List<Note> findByProfesseur_Id(Long professeurId); // Added method

    List<Note> findByProfesseurId(int professeurId);
    List<Note> findByEtudiantId(Long etudiantId);
    List<Note> findByEtudiantIdAndLangueAndSemestre(
            Long etudiantId,
            String langue,
            String semestre);
}
