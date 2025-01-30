package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {



}
