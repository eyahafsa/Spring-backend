package com.example.enicarthage.demo_proj_A.Controllers;

import com.example.enicarthage.demo_proj_A.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/data")
    public Map<String, Object> getDashboardData(
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "filiere", required = false) String filiere,
            @RequestParam(value = "classe", required = false) String classe) {
        return dashboardService.getDashboardData(year, filiere, classe);
    }

    @GetMapping("/global-stats")
    public Map<String, Object> getGlobalStats() {
        return dashboardService.getGlobalStats();
    }

    @GetMapping("/chart-data")
    public Map<String, Object> getChartData() {
        return dashboardService.getChartData();
    }

    @GetMapping("/filieres")
    public Map<String, Object> getFilieres() {
        return dashboardService.getFilieres();
    }

    @GetMapping("/classes")
    public Map<String, Object> getClasses(@RequestParam(value = "filiere") String filiere) {
        return dashboardService.getClasses(filiere);
    }

    @GetMapping("/students")
    public Map<String, Object> getStudents(@RequestParam(value = "class") String classe) {
        return dashboardService.getStudents(classe);
    }


    @GetMapping("/student-notes/{studentId}")
    public Map<String, Object> getStudentNotes(@PathVariable Long studentId) {
        return dashboardService.getStudentNotes(studentId);
    }
}