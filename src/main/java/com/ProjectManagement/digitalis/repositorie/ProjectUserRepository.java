package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRepository extends JpaRepository<ProjectUser,Long> {
}
