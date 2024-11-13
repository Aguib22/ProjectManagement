package com.ProjectManagement.digitalis.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProjet;

    private String nomProjet;
    private String descProjet;
    private Date dateDebutProjet;
    private Date dateFinProject;

    /*@OneToMany(mappedBy = "Gt")
    private List<Gt> listGt;*/

    @OneToMany(mappedBy = "projet")
    private List<Gt> listGt;


}
