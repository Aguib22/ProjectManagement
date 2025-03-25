package com.ProjectManagement.digitalis.entitie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Aguibou sow
 * @date 2025-03-16 16:32
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */
@Entity
@Data
@RequiredArgsConstructor
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;

    private Boolean fait;

    @ManyToOne
    @JoinColumn(name = "note")
    private Notes notes;
}
