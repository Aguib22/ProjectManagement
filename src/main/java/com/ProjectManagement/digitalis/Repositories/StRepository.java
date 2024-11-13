package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.SousTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StRepository extends JpaRepository<SousTache, Long> {
}
