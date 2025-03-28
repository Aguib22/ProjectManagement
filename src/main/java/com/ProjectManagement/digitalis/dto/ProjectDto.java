package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.ProjectUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-01-23 11:47
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private String nomProjet;
    private String descProjet;
    private Date dateDebutProjet;
    private Date dateFinProject;

    private Evolution evolution;
    private List<ProjectUser> projectUsers;


}
