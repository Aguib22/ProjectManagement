package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
}
