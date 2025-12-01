package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.DTO.TopStudentDTO;
import com.example.enicarthage.demo_proj_A.Models.Certification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Certification findTopByEtudiantIdAndTypeOrderByIdDesc(Long studentId, String type);


    @Query("SELECT c FROM Certification c WHERE c.type = 'TOEIC'")
    List<Certification> findAllToeic();

    @Query("SELECT c FROM Certification c WHERE c.type = 'DELF'")
    List<Certification> findAllDelf();

    @Query("SELECT c FROM Certification c JOIN c.etudiant s WHERE c.type = 'TOEIC' AND s.annee = :year ORDER BY CAST(c.score AS integer) DESC")
    List<Certification> findTopToeicStudents(String year, Pageable pageable);

    @Query("SELECT c FROM Certification c JOIN c.etudiant s WHERE c.type = 'DELF' AND s.annee = :year ORDER BY CAST(c.score AS integer) DESC")
    List<Certification> findTopDelfStudents(String year, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Certification c WHERE c.type = :type AND c.statut = :status")
    long countByTypeAndStatus(String type, String status);

    @Query("SELECT COUNT(c) FROM Certification c WHERE c.type = :type")
    long countByType(String type);

    long countByTypeContainingAndStatut(String type, String statut);

    @Query("SELECT NEW com.example.enicarthage.demo_proj_A.DTO.TopStudentDTO(" +
            "s.id, CONCAT(s.nom, ' ', s.prenom), '', " +
            "AVG(CAST(c.score AS double)), ROUND(RAND() * 100)) " +
            "FROM Certification c JOIN c.etudiant s " +
            "GROUP BY s.id, s.nom, s.prenom " +
            "ORDER BY AVG(CAST(c.score AS double)) DESC")
    List<TopStudentDTO> findTopStudents(int limit);
}