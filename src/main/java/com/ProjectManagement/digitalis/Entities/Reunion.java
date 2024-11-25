package com.ProjectManagement.digitalis.Entities;


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
public class Reunion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReunion;
    private Date dateHeureDebutReunion;
    private Date dateHeureFinReunion;

    private String ordreDuJour;
    private String remarquesR;
    private String clarifucationR;

    /*@OneToMany(mappedBy = "user")
    private List<User> listUser;*/

    @OneToMany(mappedBy = "reunion")  // Relation inverse pour la liste des utilisateurs

    private List<User> listUser;


}
