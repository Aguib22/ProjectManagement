package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {
}
