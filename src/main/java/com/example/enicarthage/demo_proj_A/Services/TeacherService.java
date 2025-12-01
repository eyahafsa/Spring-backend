package com.example.enicarthage.demo_proj_A.Services;


import com.example.enicarthage.demo_proj_A.Models.Note;
import com.example.enicarthage.demo_proj_A.Models.Student;
import com.example.enicarthage.demo_proj_A.Models.Teacher;
import com.example.enicarthage.demo_proj_A.Repositories.NoteRepository;
import com.example.enicarthage.demo_proj_A.Repositories.StudentRepository;
import com.example.enicarthage.demo_proj_A.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // Ajouter une note à un étudiant
    public Note ajouterNote(Long studentId, Long teacherId, String noteValeur, String date) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);

        if (studentOpt.isPresent() && teacherOpt.isPresent()) {
            Note note = new Note();
            note.setEtudiant(studentOpt.get());
            note.setProfesseur(teacherOpt.get());
            note.setNote(noteValeur);
            note.setDate(date);
            return noteRepository.save(note);
        }

        throw new RuntimeException("Étudiant ou professeur non trouvé");
    }

    // Récupérer toutes les notes données par un professeur
    public List<Note> getNotesByProfesseurId(int professeurId) {
        return noteRepository.findByProfesseurId(professeurId);
    }

    // Supprimer une note par son ID
    public void supprimerNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    // Mettre à jour une note
    public Note updateNote(Long noteId, String nouvelleNote, String nouvelleDate) {
        Optional<Note> noteOpt = noteRepository.findById(noteId);
        if (noteOpt.isPresent()) {
            Note note = noteOpt.get();
            note.setNote(nouvelleNote);
            note.setDate(nouvelleDate);
            return noteRepository.save(note);
        }
        throw new RuntimeException("Note non trouvée");
    }
}
