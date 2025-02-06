package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.TempsTravail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempsTravailRepository extends JpaRepository<TempsTravail,Long> {
}
