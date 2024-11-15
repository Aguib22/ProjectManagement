package com.ProjectManagement.digitalis.Controller.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class SousTacheRequest {

    private int numeroSt;
    private String tacheSt;
    private Float chargesSt;
    private Date dateDeDebutSt;
    private Date dateDeFinSt;
    private String evolutionSt;
    private Date dateDeFinReelleSt;

    private Float surchargesGt;
    private String remarquesGt;

    private Long gt;

    private Long evolution;

    private Long user;
}
