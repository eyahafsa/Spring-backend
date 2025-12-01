package com.example.enicarthage.demo_proj_A.Services;

import com.example.enicarthage.demo_proj_A.Models.Certification;
import com.example.enicarthage.demo_proj_A.Models.SchoolClass;
import com.example.enicarthage.demo_proj_A.Models.Speciality;
import com.example.enicarthage.demo_proj_A.Models.Student;
import com.example.enicarthage.demo_proj_A.Models.Note;
import com.example.enicarthage.demo_proj_A.Models.Module;
import com.example.enicarthage.demo_proj_A.Repositories.CertificationRepository;
import com.example.enicarthage.demo_proj_A.Repositories.SchoolClassRepository;
import com.example.enicarthage.demo_proj_A.Repositories.SpecialityRepository;
import com.example.enicarthage.demo_proj_A.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public Map<String, Object> getDashboardData(String year, String filiere, String classe) {
        if (year == null || year.isEmpty()) {
            year = findMostRecentYear();
        }
        System.out.println("Fetching dashboard data for year: " + year + ", filiere: " + filiere + ", classe: " + classe);
        Map<String, Object> response = new HashMap<>();

        List<Student> students = getFilteredStudents(year, filiere, classe);
        System.out.println("Filtered students: " + students.size());

        List<Certification> allToeicCerts = certificationRepository.findAllToeic();
        System.out.println("All TOEIC certifications: " + allToeicCerts.size());
        response.put("toeicScores", getHighestToeicScoresByYear(allToeicCerts));

        List<Certification> allDelfCerts = certificationRepository.findAllDelf();
        System.out.println("All DELF certifications: " + allDelfCerts.size());
        response.put("delfScores", getHighestDelfScoresByYear(allDelfCerts));

        Pageable pageable = PageRequest.of(0, 10);
        List<Certification> topToeic = certificationRepository.findTopToeicStudents(year, pageable);
        System.out.println("Top TOEIC students for " + year + ": " + topToeic.size());
        response.put("englishStudents", getTopStudents(topToeic));

        List<Certification> topDelf = certificationRepository.findTopDelfStudents(year, pageable);
        System.out.println("Top DELF students for " + year + ": " + topDelf.size());
        response.put("frenchStudents", getTopStudents(topDelf));

        response.put("averageTOEIC", calculateAverage(allToeicCerts));
        response.put("averageDELF", calculateAverage(allDelfCerts));
        response.put("top10Percent", calculateTop10Percent(students));
        response.put("studentsCount", students.size());

        List<String> filieres = specialityRepository.findAll().stream().map(Speciality::getNom).distinct().collect(Collectors.toList());
        List<String> classes = schoolClassRepository.findAll().stream().map(SchoolClass::getNom).distinct().collect(Collectors.toList());
        System.out.println("Filieres: " + filieres);
        System.out.println("Classes: " + classes);
        response.put("filieres", filieres);
        response.put("classes", classes);

        return response;
    }

    public Map<String, Object> getGlobalStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalStudents = studentRepository.count();
        stats.put("totalStudents", totalStudents);

        long validatedToeic = certificationRepository.countByTypeAndStatus("TOEIC", "validé");
        stats.put("validatedToeic", validatedToeic);

        long validatedDelf = certificationRepository.countByTypeAndStatus("DELF", "validé");
        stats.put("validatedDelf", validatedDelf);

        long failedToeic = certificationRepository.countByTypeAndStatus("TOEIC", "en cours");
        stats.put("failedToeic", failedToeic);

        long failedDelf = certificationRepository.countByTypeAndStatus("DELF", "en cours");
        stats.put("failedDelf", failedDelf);

        System.out.println("Global stats: " + stats);
        return stats;
    }

    public Map<String, Object> getChartData() {
        Map<String, Object> chartData = new HashMap<>();

        Map<String, Long> studentsByFiliere = studentRepository.findAll().stream()
                .filter(s -> s.getSpecialite() != null && s.getSpecialite().getNom() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getSpecialite().getNom(),
                        Collectors.counting()
                ));
        chartData.put("studentsByFiliere", studentsByFiliere);

        long totalToeic = certificationRepository.countByType("TOEIC");
        long validatedToeic = certificationRepository.countByTypeAndStatus("TOEIC", "validé");
        long failedToeic = certificationRepository.countByTypeAndStatus("TOEIC", "en cours");
        chartData.put("toeicData", new HashMap<String, Long>() {{
            put("validated", validatedToeic);
            put("failed", failedToeic);
            put("total", totalToeic);
        }});

        long totalDelf = certificationRepository.countByType("DELF");
        long validatedDelf = certificationRepository.countByTypeAndStatus("DELF", "validé");
        long failedDelf = certificationRepository.countByTypeAndStatus("DELF", "en cours");
        chartData.put("delfData", new HashMap<String, Long>() {{
            put("validated", validatedDelf);
            put("failed", failedDelf);
            put("total", totalDelf);
        }});

        System.out.println("Chart data: " + chartData);
        return chartData;
    }

    public Map<String, Object> getStudentNotes(Long studentId) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentRepository.findByIdWithNotesAndModules(studentId);
        if (student == null || student.getNotes() == null) {
            response.put("frenchNotes", new ArrayList<>());
            response.put("englishNotes", new ArrayList<>());
            return response;
        }

        List<Map<String, Object>> frenchNotes = new ArrayList<>();
        List<Map<String, Object>> englishNotes = new ArrayList<>();

        for (Note note : student.getNotes()) {
            Module module = note.getModule();
            if (module == null || module.getSemestre() == null || module.getLangue() == null || note.getNote() == null) {
                continue;
            }

            Map<String, Object> noteData = new HashMap<>();
            noteData.put("semestre", module.getSemestre());
            try {
                noteData.put("note", Double.parseDouble(note.getNote()));
            } catch (NumberFormatException e) {
                noteData.put("note", 0.0);
            }

            if ("Français".equalsIgnoreCase(module.getLangue())) {
                frenchNotes.add(noteData);
            } else if ("Anglais".equalsIgnoreCase(module.getLangue())) {
                englishNotes.add(noteData);
            }
        }

        // Sort by semester
        Comparator<Map<String, Object>> semesterComparator = (a, b) -> {
            String s1 = (String) a.get("semestre");
            String s2 = (String) b.get("semestre");
            return extractSemesterNumber(s1) - extractSemesterNumber(s2);
        };
        frenchNotes.sort(semesterComparator);
        englishNotes.sort(semesterComparator);

        // Ensure all semesters (S1 to S5)
        List<String> semesters = Arrays.asList("S1", "S2", "S3", "S4", "S5");
        response.put("frenchNotes", fillMissingSemesters(frenchNotes, semesters));
        response.put("englishNotes", fillMissingSemesters(englishNotes, semesters));
        return response;
    }

    private int extractSemesterNumber(String semester) {
        try {
            return Integer.parseInt(semester.replace("S", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private List<Map<String, Object>> fillMissingSemesters(List<Map<String, Object>> notes, List<String> semesters) {
        List<Map<String, Object>> filledNotes = new ArrayList<>();
        for (String semester : semesters) {
            Map<String, Object> noteData = notes.stream()
                    .filter(n -> semester.equals(n.get("semestre")))
                    .findFirst()
                    .orElseGet(() -> {
                        Map<String, Object> emptyNote = new HashMap<>();
                        emptyNote.put("semestre", semester);
                        emptyNote.put("note", 0.0);
                        return emptyNote;
                    });
            filledNotes.add(noteData);
        }
        return filledNotes;
    }

    private String findMostRecentYear() {
        List<String> years = studentRepository.findDistinctYears();
        if (years == null || years.isEmpty()) {
            return "2023-2024";
        }
        return years.stream()
                .max(Comparator.comparing(this::parseYear))
                .orElse("2023-2024");
    }

    private int parseYear(String year) {
        try {
            return Integer.parseInt(year.split("-")[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    private List<Student> getFilteredStudents(String year, String filiere, String classe) {
        if (filiere != null && !filiere.isEmpty() && classe != null && !classe.isEmpty()) {
            return studentRepository.findByAnneeAndSpecialiteNom(year, filiere)
                    .stream()
                    .filter(s -> s.getClasse() != null && s.getClasse().getNom().equals(classe))
                    .collect(Collectors.toList());
        } else if (filiere != null && !filiere.isEmpty()) {
            return studentRepository.findByAnneeAndSpecialiteNom(year, filiere);
        } else if (classe != null && !classe.isEmpty()) {
            return studentRepository.findByAnneeAndClasseNom(year, classe);
        } else {
            return studentRepository.findByAnnee(year);
        }
    }

    private List<Map<String, Object>> getHighestToeicScoresByYear(List<Certification> certifications) {
        List<Map<String, Object>> scores = new ArrayList<>();
        Map<String, Integer> yearMaxScores = certifications.stream()
                .filter(c -> c.getEtudiant() != null && c.getEtudiant().getAnnee() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getEtudiant().getAnnee(),
                        Collectors.mapping(c -> {
                            try {
                                return Integer.parseInt(c.getScore());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }, Collectors.maxBy(Integer::compare))
                ))
                .entrySet().stream()
                .filter(e -> e.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().get()
                ));
        for (Map.Entry<String, Integer> entry : yearMaxScores.entrySet()) {
            Map<String, Object> score = new HashMap<>();
            score.put("year", entry.getKey());
            score.put("score", entry.getValue());
            scores.add(score);
        }
        System.out.println("Toeic scores by year: " + scores);
        return scores;
    }

    private List<Map<String, Object>> getHighestDelfScoresByYear(List<Certification> certifications) {
        List<Map<String, Object>> scores = new ArrayList<>();
        Map<String, Integer> yearMaxScores = certifications.stream()
                .filter(c -> c.getEtudiant() != null && c.getEtudiant().getAnnee() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getEtudiant().getAnnee(),
                        Collectors.mapping(c -> {
                            try {
                                return Integer.parseInt(c.getScore());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }, Collectors.maxBy(Integer::compare))
                ))
                .entrySet().stream()
                .filter(e -> e.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().get()
                ));
        for (Map.Entry<String, Integer> entry : yearMaxScores.entrySet()) {
            Map<String, Object> score = new HashMap<>();
            score.put("year", entry.getKey());
            score.put("score", entry.getValue());
            scores.add(score);
        }
        System.out.println("Delf scores by year: " + scores);
        return scores;
    }

    private List<Map<String, Object>> getTopStudents(List<Certification> certifications) {
        return certifications.stream().map(c -> {
            Map<String, Object> student = new HashMap<>();
            student.put("id", c.getEtudiant() != null ? c.getEtudiant().getId() : 0);
            student.put("name", c.getEtudiant() != null ? (c.getEtudiant().getPrenom() + " " + c.getEtudiant().getNom()) : "Inconnu");
            student.put("score", c.getScore() != null ? c.getScore() : "0");
            student.put("class", c.getEtudiant() != null && c.getEtudiant().getClasse() != null ? c.getEtudiant().getClasse().getNom() : "Inconnu");
            student.put("filiere", c.getEtudiant() != null && c.getEtudiant().getSpecialite() != null ? c.getEtudiant().getSpecialite().getNom() : "Inconnu");
            return student;
        }).collect(Collectors.toList());
    }

    private double calculateAverage(List<Certification> certifications) {
        return certifications.stream()
                .mapToInt(c -> {
                    try {
                        return Integer.parseInt(c.getScore());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .average()
                .orElse(0);
    }

    private int calculateTop10Percent(List<Student> students) {
        return (int) Math.ceil(students.size() * 0.1);
    }

    public Map<String, Object> getFilieres() {
        Map<String, Object> response = new HashMap<>();
        List<Speciality> filieres = specialityRepository.findAll();
        response.put("filieres", filieres.stream().map(Speciality::getNom).distinct().collect(Collectors.toList()));
        return response;
    }

    public Map<String, Object> getClasses(String filiere) {
        Map<String, Object> response = new HashMap<>();
        List<SchoolClass> classes = schoolClassRepository.findByFiliere_Nom(filiere);
        response.put("classes", classes.stream().map(SchoolClass::getNom).distinct().collect(Collectors.toList()));
        return response;
    }

    public Map<String, Object> getStudents(String classe) {
        Map<String, Object> response = new HashMap<>();
        List<Student> students = studentRepository.findByClasse_Nom(classe);
        response.put("students", students.stream().map(s -> Map.of(
                "id", s.getId(),
                "name", s.getPrenom() + " " + s.getNom()
        )).distinct().collect(Collectors.toList()));
        return response;
    }
}