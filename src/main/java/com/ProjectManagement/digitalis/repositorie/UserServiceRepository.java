package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.dto.ServiceDto;
import com.ProjectManagement.digitalis.entitie.Direction;
import com.ProjectManagement.digitalis.entitie.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserServiceRepository extends JpaRepository<UserService, Long> {

    List<UserService> findByDirection(Direction direction);
}
