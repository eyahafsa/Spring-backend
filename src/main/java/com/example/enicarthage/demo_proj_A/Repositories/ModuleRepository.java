package com.example.enicarthage.demo_proj_A.Repositories;

import com.example.enicarthage.demo_proj_A.Models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("SELECT DISTINCT m.specialite.nom FROM Module m JOIN m.professeurs p WHERE p.id = :professeurId")
    List<String> findFilieresByProfesseurId(Long professeurId);

    @Query("SELECT m FROM Module m JOIN m.professeurs p WHERE m.specialite.nom = :filiere AND p.id = :professeurId")
    List<Module> findByFiliereAndProfesseurId(String filiere, Long professeurId);
}