package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface GtRepository extends JpaRepository<GrandeTache, Long> {
    List<GrandeTache> findByProjet(Projet projet);
    List<GrandeTache> findByDateDeDebutGtBetween(Date startDate, Date endDate);


}
