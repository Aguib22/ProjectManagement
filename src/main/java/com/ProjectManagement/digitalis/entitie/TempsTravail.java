package com.ProjectManagement.digitalis.entitie;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @author Aguibou sow
 * @date 2025-02-02 00:35
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class TempsTravail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private Date dateTravail;

    private Float heuresTravaillees;

    @Column(length = 500)
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "sous_tache")
    private SousTache sousTache;
}
