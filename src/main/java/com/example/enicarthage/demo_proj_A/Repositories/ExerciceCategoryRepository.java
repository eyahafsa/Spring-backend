package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.Models.ExerciseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciceCategoryRepository extends JpaRepository<ExerciseCategory, Long> {
}