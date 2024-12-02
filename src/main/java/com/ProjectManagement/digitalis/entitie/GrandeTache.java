package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class GrandeTache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGt;
    private int numeroGt;
    private String nomGt;
    private Float chargesGt;
    private Date dateDeDebutGt;
    private Date dateDeFinGt;
    private String evolutionGt;
    private Date dateDeFinReelleGt;

    @ManyToOne
    @JoinColumn(name = "projet")
    private Projet projet;

    /*@OneToMany(mappedBy = "SousTache")
    private List<SousTache> listSt;*/

    @OneToMany(mappedBy = "gt") // "gt" est le nom de l'attribut dans la classe SousTache
    @JsonIgnore
    private List<SousTache> listSt;


}
