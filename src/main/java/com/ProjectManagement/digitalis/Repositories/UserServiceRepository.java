package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserServiceRepository extends JpaRepository<UserService, Long> {

}
