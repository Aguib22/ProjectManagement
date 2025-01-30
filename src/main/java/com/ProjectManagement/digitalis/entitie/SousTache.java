package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class SousTache {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSt;
    private int numeroSt;
    private String tacheSt;
    private Float chargesSt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDeDebutSt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDeFinSt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDeFinReelleSt;


    private String remarquesGt;

    @ManyToOne
    @JoinColumn(name = "gt")
    private GrandeTache gt;

    @ManyToOne
    @JoinColumn(name = "evolution")
    private Evolution evolution;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @PrePersist
    public void prePersist() {
        if (this.evolution == null) {
            this.evolution = new Evolution();
            this.evolution.setIdTraitement(3L); // Id par défaut
            this.evolution.setEvolution("initiale"); // Nom par défaut
        }
    }

    public String toString(){

        return  "sous tache:"+tacheSt +"charge: "+chargesSt;
    }
}
