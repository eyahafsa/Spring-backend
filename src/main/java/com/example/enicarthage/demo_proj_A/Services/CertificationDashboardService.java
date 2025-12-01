package com.example.enicarthage.demo_proj_A.Services;

import com.example.enicarthage.demo_proj_A.DTO.*;
import com.example.enicarthage.demo_proj_A.Models.Certification;
import com.example.enicarthage.demo_proj_A.Models.Student;
import com.example.enicarthage.demo_proj_A.Repositories.CertificationRepository;
import com.example.enicarthage.demo_proj_A.Repositories.NoteRepository;
import com.example.enicarthage.demo_proj_A.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationDashboardService {



    private final CertificationRepository certificationRepository;
    private final StudentRepository studentRepository;
    private final NoteRepository noteRepository;


    public StudentScoresDTO getStudentScores(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        Double currentFrench = noteRepository.findLatestFrenchGradeByStudentId(studentId);
        Double currentEnglish = noteRepository.findLatestEnglishGradeByStudentId(studentId);

        Certification latestDelf = certificationRepository.findTopByEtudiantIdAndTypeOrderByIdDesc(studentId, "DELF");
        Certification latestToeic = certificationRepository.findTopByEtudiantIdAndTypeOrderByIdDesc(studentId, "TOEIC");

        return StudentScoresDTO.builder()
                .studentId(studentId)
                .studentName(student.getNom() + " " + student.getPrenom())
                .delfScore(latestDelf != null ? parseScore(latestDelf.getScore()) : null)
                .toeicScore(latestToeic != null ? parseScore(latestToeic.getScore()) : null)
                .currentFrench(currentFrench)
                .currentEnglish(currentEnglish)
                .build();
    }


    public CertificationStatsDTO getCertificationStats() {
        return CertificationStatsDTO.builder()
                .dlefB2Count(certificationRepository.countByType("DELF"))
                .toeicB2Count(certificationRepository.countByType("TOEIC"))
                .englishValidations(certificationRepository.countByTypeContainingAndStatut("ENGLISH", "VALIDATED"))
                .frenchValidations(certificationRepository.countByTypeContainingAndStatut("FRENCH", "VALIDATED"))
                .build();
    }





    private Double parseScore(String score) {
        try {
            return score != null ? Double.parseDouble(score) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<TopStudentDTO> getTopStudents(int limit) {
        return certificationRepository.findTopStudents(limit);
    }

    public List<TodayScheduleDTO> getTodaySchedule() {
        // Implémentation simplifiée - à adapter
        return List.of(
                new TodayScheduleDTO("09:00-11:00", "English Certification", "Room A12", "Prof. Smith"),
                new TodayScheduleDTO("14:00-16:00", "French Validation", "Room B07", "Prof. Dupont")
        );
    }

    public List<CertificationNoticeDTO> getNotices() {
        return List.of(
                new CertificationNoticeDTO("New DLEF Session", "Registration open for June session", LocalDate.now(), "fa-bell"),
                new CertificationNoticeDTO("TOEIC Preparation", "Workshop next Monday at 10AM", LocalDate.now().plusDays(2), "fa-calendar")
        );
    }

    public List<GradeDTO> getCurrentGrades(Long studentId) {
        // Implémentation simplifiée
        return List.of(
                new GradeDTO("Français", "16/20", 14.2, "+1.8"),
                new GradeDTO("Anglais", "15/20", 13.5, "+1.5")
        );
    }

    public List<GradeHistoryDTO> getGradeHistory(Long studentId, String semesterFilter, String subjectFilter) {
        // Implémentation simplifiée
        return List.of(
                new GradeHistoryDTO("S1 2023-2024", "Français", LocalDate.of(2023, 10, 15), "14/20", 2),
                new GradeHistoryDTO("S1 2023-2024", "Anglais", LocalDate.of(2023, 11, 20), "15/20", 3)
        );
    }
}