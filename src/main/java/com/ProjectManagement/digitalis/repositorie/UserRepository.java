package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Role;
import com.ProjectManagement.digitalis.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByMailUser(String email);
    List<User> findByRole(Role role);
    Optional<User> findByMailUser(String email);

    Optional<User> findByPasswordResetToken(String token);
}
