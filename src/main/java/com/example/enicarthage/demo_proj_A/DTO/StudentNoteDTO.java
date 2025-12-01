package com.example.enicarthage.demo_proj_A.DTO;

public class StudentNoteDTO {
    private Long id;
    private String name;
    private Double note;
    private Long moduleId;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getNote() { return note; }
    public void setNote(Double note) { this.note = note; }
    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }
}