package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.Evolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, Long> {
}
