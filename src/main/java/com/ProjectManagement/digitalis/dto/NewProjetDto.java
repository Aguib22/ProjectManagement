package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.Evolution;
import com.ProjectManagement.digitalis.entitie.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Aguibou sow
 * @date 2025-03-09 17:45
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */
@Data
public class NewProjetDto {
    private String nomProjet;
    private String descProjet;
    private List<User> projectUsers;
    private Evolution evolution;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dateDebutProjet;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dateFinProject;
}
