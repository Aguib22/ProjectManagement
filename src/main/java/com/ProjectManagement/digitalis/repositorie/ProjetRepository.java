package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByDateDebutProjetBetween(Date startDate, Date endDate);

    @Modifying
    @Transactional
    @Query("UPDATE Projet p " +
            "SET p.dateDebutProjet=:startDate, p.dateFinProject=:endDate " +
            "WHERE p.idProjet=:projectId")
    void updateStartAndEndDates(Long projectId, Date startDate, Date endDate);

    @Query("SELECT p FROM Projet p JOIN ProjectUser pu ON p.idProjet = pu.projet.id WHERE pu.user.id = :userId")
    List<Projet> findByUserId(@Param("userId") Long userId);

}
