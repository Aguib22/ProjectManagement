package com.ProjectManagement.digitalis.Repositories;


import com.ProjectManagement.digitalis.Entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

}
