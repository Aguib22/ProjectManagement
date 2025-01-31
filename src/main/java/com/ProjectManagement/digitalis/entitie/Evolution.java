package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evolution {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTraitement;
    private String evolution;

    @OneToMany(mappedBy = "evolution")
    @JsonIgnore
    private List<Projet> lstProjets;
    @OneToMany(mappedBy = "evolution")
    @JsonIgnore
    private List<GrandeTache> lisGT;

    @OneToMany(mappedBy = "evolution") // "traitement" est le nom de l'attribut dans la classe SousTache
    @JsonIgnore
    private List<SousTache> listSt;

    public String toString(){
        return "Status: "+evolution;
    }

}
