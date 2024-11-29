package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserServiceRepository extends JpaRepository<UserService, Long> {

}
