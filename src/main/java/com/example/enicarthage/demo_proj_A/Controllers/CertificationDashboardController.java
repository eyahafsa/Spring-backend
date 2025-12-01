package com.example.enicarthage.demo_proj_A.Controllers;

import com.example.enicarthage.demo_proj_A.DTO.*;
import com.example.enicarthage.demo_proj_A.Services.CertificationDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certification-dashboard")
@RequiredArgsConstructor
public class CertificationDashboardController {
    private final CertificationDashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<CertificationStatsDTO> getCertificationStats() {
        return ResponseEntity.ok(dashboardService.getCertificationStats());
    }

    @GetMapping("/top-students")
    public ResponseEntity<List<TopStudentDTO>> getTopStudents(
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(dashboardService.getTopStudents(limit));
    }

    @GetMapping("/today-schedule")
    public ResponseEntity<List<TodayScheduleDTO>> getTodaySchedule() {
        return ResponseEntity.ok(dashboardService.getTodaySchedule());
    }

    @GetMapping("/notices")
    public ResponseEntity<List<CertificationNoticeDTO>> getNotices() {
        return ResponseEntity.ok(dashboardService.getNotices());
    }

    @GetMapping("/current-grades/{studentId}")
    public ResponseEntity<List<GradeDTO>> getCurrentGrades(@PathVariable Long studentId) {
        return ResponseEntity.ok(dashboardService.getCurrentGrades(studentId));
    }

    @GetMapping("/grade-history/{studentId}")
    public ResponseEntity<List<GradeHistoryDTO>> getGradeHistory(
            @PathVariable Long studentId,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String subject) {
        return ResponseEntity.ok(dashboardService.getGradeHistory(studentId, semester, subject));
    }
}