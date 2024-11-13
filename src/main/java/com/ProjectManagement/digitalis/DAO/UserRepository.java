package com.ProjectManagement.digitalis.DAO;

import com.ProjectManagement.digitalis.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
