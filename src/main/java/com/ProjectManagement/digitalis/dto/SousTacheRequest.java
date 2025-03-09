package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.GrandeTache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SousTacheRequest {


    private int numeroSt;
    private String tacheSt;
    private Float chargesSt;
    private Long ponderation;
    private Date dateDeDebutSt;
    private Date dateDeFinSt;

    private Date dateDeFinReelleSt;

    private String remarquesGt;


    private Long idGt;
    private Long idEvolution;
    private Long Dev;

    private Long Testeur;


}
