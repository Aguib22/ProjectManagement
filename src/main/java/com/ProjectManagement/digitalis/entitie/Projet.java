package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDebutProjet;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateFinProject;

    @ManyToOne
    @JoinColumn(name = "evolution")
    @JsonBackReference
    private Evolution evolution;

    @OneToMany(mappedBy = "projet")
    @JsonIgnore
    private List<GrandeTache> listGt;


    @PrePersist
    public void prePersist() {
        if (this.evolution == null) {
            this.evolution = new Evolution();
            this.evolution.setIdTraitement(3L); // Id par défaut
            this.evolution.setEvolution("initiale"); // Nom par défaut
        }
    }
}
