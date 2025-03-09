package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Bug;
import com.ProjectManagement.digitalis.entitie.BugStatus;
import com.ProjectManagement.digitalis.entitie.SousTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug,Long> {

    List<Bug> findBySousTache(SousTache st);

    boolean existsBySousTacheAndStatus(SousTache sousTache, BugStatus status);
}
