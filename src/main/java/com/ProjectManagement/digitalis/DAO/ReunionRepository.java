package com.ProjectManagement.digitalis.DAO;

import com.ProjectManagement.digitalis.Entities.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {
}
