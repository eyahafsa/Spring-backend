package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.Models.PracticeTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PracticeTestRepository extends JpaRepository<PracticeTest, Long> {
    List<PracticeTest> findByCategoryId(Long categoryId);
}