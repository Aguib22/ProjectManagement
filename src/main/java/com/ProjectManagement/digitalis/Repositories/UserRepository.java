package com.ProjectManagement.digitalis.Repositories;

import com.ProjectManagement.digitalis.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
