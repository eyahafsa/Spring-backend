package com.example.enicarthage.demo_proj_A.Services;

import com.example.enicarthage.demo_proj_A.Models.Course;
import com.example.enicarthage.demo_proj_A.Models.CourseCategory;
import com.example.enicarthage.demo_proj_A.Repositories.CourseCategoryRepository;
import com.example.enicarthage.demo_proj_A.Repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseCategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public List<CourseCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Course> getFeaturedCourses() {
        return courseRepository.findByFeaturedTrue();
    }
}