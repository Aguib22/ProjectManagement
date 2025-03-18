package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Long> {

    @Query("SELECT n FROM Notes n JOIN n.user u WHERE u.idUser = :userId")
    List<Notes> findByUserId(@Param("userId") Long userId);

}
