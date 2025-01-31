package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.Projet;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrandeTacheRequest {

    private String nomGt;
    private Float chargesGt;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateDeDebutGt;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateDeFinGt;

    private Evolution evolution;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateDeFinReelleGt;

    private Projet projet;

}
