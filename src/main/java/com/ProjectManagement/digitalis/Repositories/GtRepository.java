package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.GrandeTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GtRepository extends JpaRepository<GrandeTache, Long> {
}
