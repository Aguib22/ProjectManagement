package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.SousTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StRepository extends JpaRepository<SousTache, Long> {
}
