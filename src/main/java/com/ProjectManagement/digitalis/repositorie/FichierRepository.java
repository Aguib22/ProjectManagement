package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Fichier;
import com.ProjectManagement.digitalis.entitie.Repertoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichierRepository extends JpaRepository<Fichier,Long> {
    List<Fichier> findByRepertoire(Repertoir repertoir);
}
