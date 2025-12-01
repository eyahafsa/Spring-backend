package com.example.enicarthage.demo_proj_A.Controllers;

import com.example.enicarthage.demo_proj_A.DTO.MoyenneAnglaisDTO;
import com.example.enicarthage.demo_proj_A.DTO.MoyenneFrancaisDTO;
import com.example.enicarthage.demo_proj_A.DTO.NoteDTO;
import com.example.enicarthage.demo_proj_A.DTO.StudentNoteDTO;
import com.example.enicarthage.demo_proj_A.Models.Note;
import com.example.enicarthage.demo_proj_A.Services.NoteService;
import com.example.enicarthage.demo_proj_A.Utils.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private final NoteService noteService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }
    @GetMapping("/filieres")
    public List<String> getFilieresForTeacher() {
        Long professeurId = getProfesseurIdFromRequest();
        return noteService.getFilieresForTeacher(professeurId);
    }

    @GetMapping("/classes")
    public List<String> getClassesForFiliere(@RequestParam String filiere) {
        Long professeurId = getProfesseurIdFromRequest();
        return noteService.getClassesForFiliere(filiere, professeurId);
    }

    @GetMapping("/etudiant/{id}")
    public List<NoteDTO> getNotes(@PathVariable Long id) {
        return noteService.getNotesForStudent(id);
    }
    @GetMapping("/moyenne/francais/{etudiantId}/{semestre}")
    public ResponseEntity<?> getMoyenneFrancaisParSemestre(
            @PathVariable Long etudiantId,
            @PathVariable String semestre) {

        try {
            double moyenne = noteService.calculerMoyenneFrancaisParSemestre(etudiantId, semestre);
            return ResponseEntity.ok(new MoyenneFrancaisDTO(semestre, moyenne));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Aucune note de français trouvée pour ce semestre"));
        }
    }
    @GetMapping("/moyenne/anglais/{etudiantId}/{semestre}")
    public ResponseEntity<?> getMoyenneAnglaisParSemestre(
            @PathVariable Long etudiantId,
            @PathVariable String semestre) {

        try {
            double moyenne = noteService.calculerMoyenneFrancaisParSemestre(etudiantId, semestre);
            return ResponseEntity.ok(new MoyenneFrancaisDTO(semestre, moyenne));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Aucune note de français trouvée pour ce semestre"));
        }
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec ID: " + id));
    }

    @PostMapping("/save")
    public void saveNote(@RequestBody NoteDTO noteDTO) {
        Long professeurId = getProfesseurIdFromRequest();
        noteService.saveNote(noteDTO, professeurId);
    }
    @GetMapping("/students")
    public List<StudentNoteDTO> getStudentsForClass(
            @RequestParam(required = true) String filiere,
            @RequestParam(required = true) String classe) {
        if (filiere == null || filiere.isEmpty()) {
            throw new IllegalArgumentException("Filiere is required");
        }
        if (classe == null || classe.isEmpty()) {
            throw new IllegalArgumentException("Classe is required");
        }
        Long professeurId = getProfesseurIdFromRequest();
        return noteService.getStudentsForClass(filiere, classe, professeurId);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        return noteService.updateNote(id, updatedNote);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }
    private Long getProfesseurIdFromRequest() {
        Long professeurId = (Long) request.getAttribute("professeurId");
        if (professeurId == null) {
            throw new RuntimeException("Professeur ID not found in request");
        }
        return professeurId;
    }
}

