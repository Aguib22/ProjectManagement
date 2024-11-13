package com.ProjectManagement.digitalis.DAO;

import com.ProjectManagement.digitalis.Entities.St;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StRepository extends JpaRepository<St, Long> {
}
