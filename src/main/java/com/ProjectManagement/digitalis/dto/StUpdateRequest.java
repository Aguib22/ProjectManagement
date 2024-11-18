package com.ProjectManagement.digitalis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StUpdateRequest {

    private Long idSt; // Nécessaire pour identifier la sous-tâche à mettre à jour
    private int numeroSt;
    private String tacheSt;
    private Float chargesSt;
    private Date dateDeDebutSt;
    private Date dateDeFinSt;
    private String evolutionSt;
    private Date dateDeFinReelleSt;
    private Float surchargesGt;
    private String remarquesGt;
    private Long idGt;
    private Long idEvolution;
    private Long idUser;
}
