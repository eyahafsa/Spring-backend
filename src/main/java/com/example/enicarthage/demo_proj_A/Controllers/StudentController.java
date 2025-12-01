package com.example.enicarthage.demo_proj_A.Controllers;

import com.example.enicarthage.demo_proj_A.DTO.*;
import com.example.enicarthage.demo_proj_A.Services.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final CertificationDashboardService certificationService;

    public StudentController(StudentService studentService,
                             CertificationDashboardService certificationService) {
        this.studentService = studentService;
        this.certificationService = certificationService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        try {
            List<StudentDTO> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération des étudiants", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        try {
            Optional<StudentDTO> student = Optional.ofNullable(studentService.getStudentById(id));
            return student.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération de l'étudiant", e);
        }
    }

    @GetMapping("/{id}/scores")
    public ResponseEntity<StudentScoresDTO> getStudentScores(@PathVariable Long id) {
        try {
            StudentScoresDTO scores = certificationService.getStudentScores(id);
            return ResponseEntity.ok(scores);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération des scores", e);
        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO created = studentService.createStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Erreur lors de la création de l'étudiant", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO studentDTO) {
        try {
            Optional<StudentDTO> updated = Optional.ofNullable(studentService.updateStudent(id, studentDTO));
            return updated.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Erreur lors de la mise à jour de l'étudiant", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            if (studentService.deleteStudent(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la suppression de l'étudiant", e);
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopStudentDTO>> getTopStudents(
            @RequestParam(defaultValue = "5") int limit) {
        try {
            List<TopStudentDTO> topStudents = certificationService.getTopStudents(limit);
            return ResponseEntity.ok(topStudents);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération du classement", e);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<CertificationStatsDTO> getCertificationStats() {
        try {
            CertificationStatsDTO stats = certificationService.getCertificationStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération des statistiques", e);
        }
    }
}