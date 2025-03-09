package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @Column(columnDefinition = "bigint default 1")
    private Long ponderation = 1L;
    @OneToMany(mappedBy = "sousTache", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TempsTravail> tempsTravaux = new ArrayList<>();

    @OneToMany(mappedBy = "sousTache",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Bug> bugs;
    boolean dragable = true;
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

    public void updateDragable() {
        if (bugs == null || bugs.isEmpty()) {
            this.dragable = true; // Aucune bug, donc la sous-tâche est "dragable"
        }  else if (bugs.stream().anyMatch(bug -> bug.getStatus() == BugStatus.EN_ATTENTE)) {
            this.dragable = false; // Au moins un bug "EN_ATTENTE", on bloque le drag
        } else {
            this.dragable = true; // Sinon, c'est bon, on laisse draggable à true
        }
    }
    @PreUpdate
    public void preUpdate() {
        updateDragable();
    }


    public String toString(){

        return  "sous tache:"+tacheSt +"charge: "+chargesSt;
    }
}
