package com.example.enicarthage.demo_proj_A.Controllers;


import com.example.enicarthage.demo_proj_A.Models.Note;
import com.example.enicarthage.demo_proj_A.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Ajouter une note
    @PostMapping("/add-note")
    public Note ajouterNote(
            @RequestParam Long studentId,
            @RequestParam Long teacherId,
            @RequestParam String valeur,
            @RequestParam String date) {
        return teacherService.ajouterNote(studentId, teacherId, valeur, date);
    }

    // Récupérer toutes les notes d’un professeur
    @GetMapping("/{professeurId}/notes")
    public List<Note> getNotesByProf(@PathVariable int professeurId) {
        return teacherService.getNotesByProfesseurId(professeurId);
    }

    // Supprimer une note
    @DeleteMapping("/note/{noteId}")
    public void supprimerNote(@PathVariable Long noteId) {
        teacherService.supprimerNote(noteId);
    }

    // Mettre à jour une note
    @PutMapping("/update-note/{noteId}")
    public Note updateNote(
            @PathVariable Long noteId,
            @RequestParam String nouvelleValeur,
            @RequestParam String nouvelleDate) {
        return teacherService.updateNote(noteId, nouvelleValeur, nouvelleDate);
    }
}
