package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String nomGt;
    private Float chargesGt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDeDebutGt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDeFinGt;
    //private String evolutionGt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDeFinReelleGt;

    @ManyToOne
    @JoinColumn(name = "evolution")
    private Evolution evolution;

    @ManyToOne
    @JoinColumn(name = "projet")
    private Projet projet;

    /*@OneToMany(mappedBy = "SousTache")
    private List<SousTache> listSt;*/

    @OneToMany(mappedBy = "gt" ,cascade = CascadeType.ALL, orphanRemoval = true) // "gt" est le nom de l'attribut dans la classe SousTache
    @JsonIgnore
    private List<SousTache> listSt;


    @PrePersist
    public void prePersist() {
        if (this.evolution == null) {
            this.evolution = new Evolution();
            this.evolution.setIdTraitement(3L); // Id par défaut
            this.evolution.setEvolution("initiale"); // Nom par défaut
        }
    }

    public String toString(){
        return "tache:"+nomGt +"charge: "+chargesGt;
    }

}
