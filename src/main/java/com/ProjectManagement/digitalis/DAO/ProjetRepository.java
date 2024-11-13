package com.ProjectManagement.digitalis.DAO;

import com.ProjectManagement.digitalis.Entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
}