package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByDateDebutProjetBetween(Date startDate, Date endDate);
}
