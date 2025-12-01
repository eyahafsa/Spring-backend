package com.example.enicarthage.demo_proj_A.Controllers;

import com.example.enicarthage.demo_proj_A.Models.Course;
import com.example.enicarthage.demo_proj_A.Models.CourseCategory;
import com.example.enicarthage.demo_proj_A.Services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// CourseController.java
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/categories")
    public ResponseEntity<List<CourseCategory>> getCourseCategories() {
        return ResponseEntity.ok(courseService.getAllCategories());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Course>> getFeaturedCourses() {
        return ResponseEntity.ok(courseService.getFeaturedCourses());
    }
}