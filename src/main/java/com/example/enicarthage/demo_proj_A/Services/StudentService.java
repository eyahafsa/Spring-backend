package com.example.enicarthage.demo_proj_A.Services;
import com.example.enicarthage.demo_proj_A.DTO.StudentDTO;
import com.example.enicarthage.demo_proj_A.Models.Student;
import com.example.enicarthage.demo_proj_A.Models.SchoolClass;
import com.example.enicarthage.demo_proj_A.Models.Speciality;
import com.example.enicarthage.demo_proj_A.Repositories.StudentRepository;
import com.example.enicarthage.demo_proj_A.Repositories.SchoolClassRepository;
import com.example.enicarthage.demo_proj_A.Repositories.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getNom(),
                student.getPrenom(),
                student.getAnnee(),
                student.getEmail(),
                student.getClasse() != null ? student.getClasse().getId() : 0,
                student.getClasse() != null ? student.getClasse().getNom() : null,
                student.getSpecialite() != null ? student.getSpecialite().getId() : 0,
                student.getSpecialite() != null ? student.getSpecialite().getNom() : null
        );
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setNom(dto.getNom());
        student.setPrenom(dto.getPrenom());
        student.setAnnee(dto.getAnnee());
        student.setEmail(dto.getEmail());

        Optional<SchoolClass> classe = schoolClassRepository.findById(dto.getClasseId());
        classe.ifPresent(student::setClasse);

        Optional<Speciality> specialite = specialityRepository.findById(dto.getSpecialiteId());
        specialite.ifPresent(student::setSpecialite);

        return student;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(this::convertToDTO).orElse(null);
    }

    public StudentDTO createStudent(StudentDTO dto) {
        Student student = convertToEntity(dto);
        Student saved = studentRepository.save(student);
        return convertToDTO(saved);
    }

    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isPresent()) {
            Student student = convertToEntity(dto);
            student.setId(id);
            Student updated = studentRepository.save(student);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Student getCurrentUser() {
        return studentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun étudiant trouvé."));
    }

}

