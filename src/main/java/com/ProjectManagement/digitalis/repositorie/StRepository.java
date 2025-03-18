package com.ProjectManagement.digitalis.repositorie;

import com.ProjectManagement.digitalis.entitie.GrandeTache;
import com.ProjectManagement.digitalis.entitie.SousTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface StRepository extends JpaRepository<SousTache, Long> {

    List<SousTache> findByGt(GrandeTache grandeTache);
    List<SousTache> findByDateDeDebutStBetween(Date startDate, Date endDate);

    @Query("SELECT st FROM SousTache st" +
           " WHERE (st.dev.idUser = :userId OR st.testeur.idUser = :userId) "+
            "AND st.gt.idGt = :idGt")
    List<SousTache> findSousTachesByUserIdAndGrandeTache(
            @Param("userId") Long userId,
            @Param("idGt") Long idGt);

}
