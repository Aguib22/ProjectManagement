package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

}
