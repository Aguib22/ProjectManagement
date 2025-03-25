package com.ProjectManagement.digitalis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Aguibou sow
 * @date 2025-03-20 13:15
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */
@Data
@AllArgsConstructor
public class ChangePwdDefaultRequest {

    private String mailUser;
    private String password;
}
