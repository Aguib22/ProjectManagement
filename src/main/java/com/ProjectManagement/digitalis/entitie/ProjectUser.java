package com.ProjectManagement.digitalis.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Aguibou sow
 * @date 2025-03-09 17:28
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_users")
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_projet", referencedColumnName = "id_projet", nullable = false)
    private Projet projet;


    private LocalDateTime createdAt;
    private boolean enabled = true;
}
