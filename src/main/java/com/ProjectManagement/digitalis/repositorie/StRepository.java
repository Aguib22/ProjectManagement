package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.SousTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StRepository extends JpaRepository<SousTache, Long> {

    List<SousTache> findByGt(GrandeTache grandeTache);
}
