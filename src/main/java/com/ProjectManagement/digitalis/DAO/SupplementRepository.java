package com.ProjectManagement.digitalis.DAO;

import com.ProjectManagement.digitalis.Entities.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {
}
