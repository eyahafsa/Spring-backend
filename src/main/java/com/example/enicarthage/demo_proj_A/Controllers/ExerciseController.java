package com.example.enicarthage.demo_proj_A.Controllers;
import com.example.enicarthage.demo_proj_A.Models.*;
import com.example.enicarthage.demo_proj_A.Services.CourseService;
import com.example.enicarthage.demo_proj_A.Services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ExerciseController.java
@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/categories")
    public ResponseEntity<List<ExerciseCategory>> getExerciseCategories() {
        return ResponseEntity.ok(exerciseService.getAllCategories());
    }

    @GetMapping("/tests")
    public ResponseEntity<List<PracticeTest>> getPracticeTests() {
        return ResponseEntity.ok(exerciseService.getPracticeTests());
    }

    @GetMapping("/activities")
    public ResponseEntity<List<RecentActivity>> getRecentActivities() {
        return ResponseEntity.ok(exerciseService.getRecentActivities());
    }

    @GetMapping("/progress")
    public ResponseEntity<UserProgress> getUserProgress() {
        return ResponseEntity.ok(exerciseService.getUserProgress());
    }
}