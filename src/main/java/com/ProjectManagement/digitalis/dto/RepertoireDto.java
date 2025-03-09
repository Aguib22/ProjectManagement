package com.ProjectManagement.digitalis.dto;

import lombok.Data;

/**
 * @author Aguibou sow
 * @date 2025-02-18 11:59
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */

@Data
public class RepertoireDto {

    private String nom;

    private String description;
    private Long parentId;
}
