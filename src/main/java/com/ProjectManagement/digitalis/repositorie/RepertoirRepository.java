package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Repertoir;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepertoirRepository extends JpaRepository<Repertoir,Long> {

    List<Repertoir> findByParentIsNull();
    Repertoir findByNom(String nom);
    @EntityGraph(attributePaths = {"sousRepertoires", "fichiers"})
    Optional<Repertoir> findById(Long id);
}
