package com.ProjectManagement.digitalis.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGt;
    private int numeroGt;
    private String tacheGt;
    private String chargesGt;
    private Date dateDeDebutGt;
    private Date dateDeFinGt;
    private String evolutionGt;
    private Date dateDeFinReelleGt;

    @ManyToOne
    @JoinColumn(name = "projet")
    private Projet projet;

    /*@OneToMany(mappedBy = "St")
    private List<St> listSt;*/

    @OneToMany(mappedBy = "gt") // "gt" est le nom de l'attribut dans la classe St
    private List<St> listSt;


}
