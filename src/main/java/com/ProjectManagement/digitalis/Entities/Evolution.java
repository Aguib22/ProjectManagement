package com.ProjectManagement.digitalis.Entities;


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

   /* @OneToMany(mappedBy = "SousTache")
    private List<SousTache> listSt;*/

    @OneToMany(mappedBy = "traitement") // "traitement" est le nom de l'attribut dans la classe SousTache
    private List<SousTache> listSt;

}
