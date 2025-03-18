package com.ProjectManagement.digitalis.entitie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-03-16 14:09
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */

@Entity
@Table(name = "notes")
@Data
@RequiredArgsConstructor
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    private String imageUrl; // URL de l'image


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> tache = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
