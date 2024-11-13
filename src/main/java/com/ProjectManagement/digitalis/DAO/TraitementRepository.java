package com.ProjectManagement.digitalis.DAO;

import com.ProjectManagement.digitalis.Entities.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TraitementRepository extends JpaRepository<Traitement, Long> {
}
