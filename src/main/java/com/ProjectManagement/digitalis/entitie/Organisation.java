package com.ProjectManagement.digitalis.entitie;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrganisation;

    private String nomOrganisation;

    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "organisation-direction")
    private List<Direction> directions;

}
