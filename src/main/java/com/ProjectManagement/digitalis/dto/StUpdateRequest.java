package com.ProjectManagement.digitalis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< HEAD
import lombok.NoArgsConstructor;

import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class StUpdateRequest {

=======

import java.util.Date;

@Data
@AllArgsConstructor
public class StUpdateRequest {


>>>>>>> 7b9f9b4e2fe52f746c755058d76765c0174cc3d6
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
