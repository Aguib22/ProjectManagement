package com.ProjectManagement.digitalis.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aguibou sow
 * @date 2025-02-08 23:58
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugDto {

    private String description;

    private String captureUrl;

    private Long testeur;

    private Long sousTache;

}
