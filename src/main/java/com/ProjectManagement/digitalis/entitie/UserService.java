package com.ProjectManagement.digitalis.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserService;

    private String nomUserService;

    @ManyToOne
    @JoinColumn(name = "idDirection", nullable = false)
    private Direction direction;

    @OneToMany(mappedBy = "userService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}
