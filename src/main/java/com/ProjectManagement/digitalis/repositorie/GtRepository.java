package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.GrandeTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GtRepository extends JpaRepository<GrandeTache, Long> {
}
