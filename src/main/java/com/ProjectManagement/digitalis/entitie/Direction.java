package com.ProjectManagement.digitalis.entitie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDirection;

    private String nomDirection;

    @ManyToOne
    @JoinColumn(name = "organisation", nullable = false)
    @JsonBackReference(value = "organisation-direction")
    private Organisation organisation;

    @OneToMany(mappedBy = "direction", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<UserService> userServices;

}