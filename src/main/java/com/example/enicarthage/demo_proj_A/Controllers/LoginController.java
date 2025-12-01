package com.example.enicarthage.demo_proj_A.Controllers;
import com.example.enicarthage.demo_proj_A.DTO.LoginRequest;
import com.example.enicarthage.demo_proj_A.Models.Student;
import com.example.enicarthage.demo_proj_A.Repositories.DirectionRepository;
import com.example.enicarthage.demo_proj_A.Repositories.StudentRepository;
import com.example.enicarthage.demo_proj_A.Repositories.TeacherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private DirectionRepository directionRepo;


    @PostMapping("/student")
    public ResponseEntity<Map<String, Object>> loginStudent(@Valid @RequestBody LoginRequest request) {
        return studentRepo.findByEmailAndMotDePasse(request.getEmail(), request.getPassword())
                .map(student -> {
                    Map<String, Object> response = new HashMap<>();

                    response.put("student", Map.of(
                            "id", student.getId()
                    ));

                    response.put("authState", Map.of(
                            "access", Map.of(
                                    "token", "st-" + student.getId() + "-" + System.currentTimeMillis(),
                                    "expires", System.currentTimeMillis() + 3600000 // 1 heure
                            )
                    ));

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    @PostMapping("/teacher")
    public ResponseEntity<?> loginTeacher(@Valid @RequestBody LoginRequest request) {
        return teacherRepo.findByEmailAndMotDePasse(request.getEmail(), request.getPassword())
                .map(student -> {
                    Map<String, Object> response = new HashMap<>();

                    response.put("teacher", Map.of(
                            "id", student.getId()
                    ));

                    response.put("authState", Map.of(
                            "access", Map.of(
                                    "token", "st-" + student.getId() + "-" + System.currentTimeMillis(),
                                    "expires", System.currentTimeMillis() + 3600000 // 1 heure
                            )
                    ));

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    @PostMapping("/direction")
    public ResponseEntity<?> loginDirection(@Valid @RequestBody LoginRequest request) {
        return directionRepo.findByEmailAndMotDePasse(request.getEmail(), request.getPassword())
                .map(direction -> {
                    Map<String, Object> response = new HashMap<>();

                    response.put("direction", Map.of(
                            "id", direction.getId()
                    ));

                    response.put("authState", Map.of(
                            "access", Map.of(
                                    "token", "st-" + direction.getId() + "-" + System.currentTimeMillis(),
                                    "expires", System.currentTimeMillis() + 3600000 // 1 heure
                            )
                    ));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
