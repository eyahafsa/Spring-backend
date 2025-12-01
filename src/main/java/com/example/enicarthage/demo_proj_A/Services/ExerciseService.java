package com.example.enicarthage.demo_proj_A.Services;


import com.example.enicarthage.demo_proj_A.Models.*;
import com.example.enicarthage.demo_proj_A.Repositories.*;

import com.example.enicarthage.demo_proj_A.Repositories.PracticeTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// ExerciseService.java
@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciceCategoryRepository categoryRepository;
    private final PracticeTestRepository testRepository;
    private final RecentActivityRepository activityRepository;
    private final UserProgressRepository progressRepository;
    private final StudentService userService;

    public List<ExerciseCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<PracticeTest> getPracticeTests() {
        return testRepository.findAll();
    }

    public List<RecentActivity> getRecentActivities() {
        return activityRepository.findTop5ByOrderByDateDesc();
    }

    public UserProgress getUserProgress() {
        Student user = userService.getCurrentUser();
        return progressRepository.findByUser(user)
                .orElseGet(() -> UserProgress.builder()
                        .user(user)
                        .exercisesCompleted(0)
                        .averageScore(0.0)
                        .timePracticed("0h 0m")
                        .build());
    }
}