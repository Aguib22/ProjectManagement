package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {
}
