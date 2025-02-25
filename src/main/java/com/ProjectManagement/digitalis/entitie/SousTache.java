package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private Float chargesStHeures;

    private Float surcharge;
    @Column(length = 255)
    private String observation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dateDeDebutSt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dateDeFinSt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dateDeFinReelleSt;

    @Column(length = 1000)
    private String remarquesGt;

    @ManyToOne
    @JoinColumn(name = "gt")
    private GrandeTache gt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "evolution")
    private Evolution evolution;

    @ManyToOne
    @JoinColumn(name = "dev")
    private User dev;

    @ManyToOne
    @JoinColumn(name = "testeur")
    private User testeur;

    @OneToMany(mappedBy = "sousTache", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TempsTravail> tempsTravaux = new ArrayList<>();

    @OneToMany(mappedBy = "sousTache",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bug> bugs;
    @PrePersist
    public void prePersist() {
        if (this.evolution == null) {
            this.evolution = new Evolution();
            this.evolution.setIdTraitement(3L); // Id par défaut
            this.evolution.setEvolution("initiale"); // Nom par défaut
        }
    }
    public void chargesStHeures(Float chargesStJours) {
        if (chargesStJours != null) {
            this.chargesStHeures = chargesStJours * 8; // 1 jour = 8 heures
        }
    }
    public Float getTotalHeuresTravaillees() {
        return tempsTravaux.stream().map(TempsTravail::getHeuresTravaillees).reduce(0f,Float::sum);
    }

    public boolean isTacheComplete() {
        return getTotalHeuresTravaillees() >= chargesStHeures;
    }


    public String toString(){

        return  "sous tache:"+tacheSt +"charge: "+chargesSt;
    }
}
