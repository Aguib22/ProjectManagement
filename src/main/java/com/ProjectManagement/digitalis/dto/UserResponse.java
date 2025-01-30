package com.ProjectManagement.digitalis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}
