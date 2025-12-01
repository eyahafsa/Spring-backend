package com.example.enicarthage.demo_proj_A.DTO;

import lombok.Data;

@Data
public class NoteDTO {
    private Long id;
    private String note;
    private String date;
    private String langue;
    private String niveau;
    private String semestre;
    private double seuilValidation;

    private Long etudiantId;
    private String etudiantNom;
    private Long professeurId;
    private String professeurNom;
    private Long moduleId;


    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }
    public Long getProfesseurId() { return professeurId; }
    public void setProfesseurId(Long professeurId) { this.professeurId = professeurId; }
}
