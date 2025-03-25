package com.ProjectManagement.digitalis.entitie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Aguibou sow
 * @date 2025-03-20 08:31
 * @package com.ProjectManagement.digitalis.entitie
 * @project digitalis
 */
@Entity
@Table(name = "userTokenFmc")
@Data
@RequiredArgsConstructor
public class UserTokenFmc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String token;
}
