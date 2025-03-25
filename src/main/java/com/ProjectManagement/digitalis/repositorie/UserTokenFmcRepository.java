package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.UserTokenFmc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenFmcRepository extends JpaRepository<UserTokenFmc,Long> {
    Optional<UserTokenFmc> findByUserId(Long userId);
}
