package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {
}
