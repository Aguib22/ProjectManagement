package com.ProjectManagement.digitalis.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class St {


    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSt;
    private int numeroSt;
    private String tacheSt;
    private String chargesSt;
    private Date dateDeDebutSt;
    private Date dateDeFinSt;
    private String evolutionSt;
    private Date dateDeFinReelleSt;

    private String surchargesGt;
    private String remarquesGt;

    @ManyToOne  @JoinColumn(name = "idGt")
    private Gt gt;

    @ManyToOne @JoinColumn(name = "idTraitement")
    private Traitement traitement;

    @ManyToOne @JoinColumn(name = "idUser")
    private User user;
}