package com.ProjectManagement.digitalis.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Long matriculeUser;
    private String prenomUser;
    private String nomUser;
    private int numeroUser;
    private String mailUser;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<SousTache> listSt;

    @ManyToOne
    @JoinColumn(name = "reunion")
    private Reunion reunion;
}
