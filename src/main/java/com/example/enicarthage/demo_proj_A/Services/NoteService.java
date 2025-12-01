package com.example.enicarthage.demo_proj_A.Services;


import com.example.enicarthage.demo_proj_A.DTO.NoteDTO;
import com.example.enicarthage.demo_proj_A.DTO.StudentNoteDTO;
import com.example.enicarthage.demo_proj_A.Models.Note;
import com.example.enicarthage.demo_proj_A.Models.Student;
import com.example.enicarthage.demo_proj_A.Models.Module;

import com.example.enicarthage.demo_proj_A.Models.Teacher;
import com.example.enicarthage.demo_proj_A.Repositories.ModuleRepository;
import com.example.enicarthage.demo_proj_A.Repositories.NoteRepository;
import com.example.enicarthage.demo_proj_A.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private StudentRepository studentRepository;



    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note updatedNote) {
        updatedNote.setId(id);
        return noteRepository.save(updatedNote);
    }
    public List<String> getFilieresForTeacher(Long professeurId) {
        return moduleRepository.findFilieresByProfesseurId(professeurId);
    }
    public List<String> getClassesForFiliere(String filiere, Long professeurId) {
        return studentRepository.findClassesByFiliereAndProfesseur(filiere, professeurId);
    }

    public List<StudentNoteDTO> getStudentsForClass(String filiere, String classe, Long professeurId) {
        List<Student> students = studentRepository.findByFiliereAndClasse(filiere, classe);
        List<Module> modules = moduleRepository.findByFiliereAndProfesseurId(filiere, professeurId);
        if (modules.isEmpty()) {
            return Collections.emptyList();
        }

        Long moduleId = modules.get(0).getId();

        return students.stream().map(student -> {
            Note note = noteRepository.findByEtudiantIdAndModuleId(student.getId(), moduleId);
            StudentNoteDTO dto = new StudentNoteDTO();
            dto.setId(student.getId());
            dto.setName(student.getNom() + " " + student.getPrenom());
            dto.setNote(note != null ? Double.parseDouble(note.getNote()) : null);
            dto.setModuleId(moduleId);
            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
    public List<NoteDTO> getNotesForStudent(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId).stream().map(this::convert).toList();
    }

    public void saveNote(NoteDTO noteDTO, Long professeurId) {
        Student student = studentRepository.findById(noteDTO.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Module module = moduleRepository.findById(noteDTO.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found"));
        Teacher teacher = new Teacher();
        // Convert Long to int with range check
        if (professeurId > Integer.MAX_VALUE || professeurId < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("professeurId is out of range for an int: " + professeurId);
        }
        teacher.setId(professeurId); // Convert Long to int

        Note note = noteRepository.findByEtudiantIdAndModuleId(noteDTO.getEtudiantId(), noteDTO.getModuleId());
        if (note == null) {
            note = new Note();
            note.setEtudiant(student);
            note.setModule(module);
            note.setProfesseur(teacher);
            note.setDate(LocalDate.now().toString());
        }
        note.setNote(String.valueOf(noteDTO.getNote()));
        noteRepository.save(note);
    }

    private NoteDTO convert(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setNote(note.getNote());
        dto.setDate(note.getDate());
        dto.setEtudiantId(note.getEtudiant().getId());
        dto.setEtudiantNom(note.getEtudiant().getNom());
        dto.setNiveau(note.getNiveau());
        dto.setLangue(note.getLangue());
        dto.setSemestre(note.getSemestre());
        dto.setSeuilValidation(note.getSeuilValidation());
        dto.setProfesseurId(note.getProfesseur().getId());
        dto.setProfesseurNom(note.getProfesseur().getNom());
        return dto;
    }
    public double calculerMoyenneFrancaisParSemestre(Long etudiantId, String semestre) {
        List<Note> notes = noteRepository.findByEtudiantIdAndLangueAndSemestre(
                etudiantId,
                "FRANCAIS",
                semestre);

        if (notes.isEmpty()) {
            throw new RuntimeException("Aucune note trouvée pour ce semestre");
        }

        return notes.stream()
                .mapToDouble(note -> Double.parseDouble(note.getNote()))
                .average()
                .orElse(0.0);
    }
    public double calculerMoyenneAnglaisParSemestre(Long etudiantId, String semestre) {
        List<Note> notes = noteRepository.findByEtudiantIdAndLangueAndSemestre(
                etudiantId,
                "ANGLAIS",
                semestre);

        if (notes.isEmpty()) {
            throw new RuntimeException("Aucune note trouvée pour ce semestre");
        }

        return notes.stream()
                .mapToDouble(note -> Double.parseDouble(note.getNote()))
                .average()
                .orElse(0.0);
    }
}
