package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Aguibou sow
 * @date 2025-01-01 13:24
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */

@Data
@AllArgsConstructor
public class UserResponse {

    private Long idUser;
    private String prenomUser;
    private String nomUser;
    private String mailUser;

    private Role role;
    private boolean temporaryPassword;
}
