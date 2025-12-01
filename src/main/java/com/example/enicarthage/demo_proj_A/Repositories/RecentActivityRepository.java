package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.Models.RecentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentActivityRepository extends JpaRepository<RecentActivity, Long> {

    List<RecentActivity> findTop5ByOrderByDateDesc();
}
