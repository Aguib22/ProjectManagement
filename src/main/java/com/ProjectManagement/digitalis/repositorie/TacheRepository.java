package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache,Long> {
    @Query("SELECT t FROM Tache t WHERE t.notes.id = :noteId")
    List<Tache> findByNoteId(@Param("noteId") Long noteId);

}
