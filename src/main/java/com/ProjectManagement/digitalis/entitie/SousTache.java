package com.ProjectManagement.digitalis.entitie;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SousTache {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSt;
    private int numeroSt;
    private String tacheSt;
    private Float chargesSt;
    private Date dateDeDebutSt;
    private Date dateDeFinSt;
    private String evolutionSt;
    private Date dateDeFinReelleSt;

    private Float surchargesGt;
    private String remarquesGt;

    @ManyToOne
    @JoinColumn(name = "idGt")
    private GrandeTache gt;

    @ManyToOne
    @JoinColumn(name = "idTraitement")
    private Evolution traitement;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}
