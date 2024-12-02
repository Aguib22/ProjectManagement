package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

}
