package com.ProjectManagement.digitalis.repositorie;


import com.ProjectManagement.digitalis.entitie.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

}
