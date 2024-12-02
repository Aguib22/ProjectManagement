package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Evolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, Long> {
}
