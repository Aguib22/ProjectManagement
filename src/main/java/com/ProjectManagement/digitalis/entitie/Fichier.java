package com.ProjectManagement.digitalis.entitie;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @author Aguibou sow
 * @date 2025-02-18 11:15
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */
@Entity
@Table
@Data
public class Fichier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String type;
    private String cheminStockage;
    private String description;

    @ManyToOne
    @JoinColumn(name = "repertoire_id")
    private Repertoir repertoire;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date uploadAt;
}
