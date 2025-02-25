package com.ProjectManagement.digitalis.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aguibou sow
 * @date 2025-02-18 12:02
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */
@Data
public class FichierDto {

    private String nom;
    private Long repertoireId;
    private String description;
    MultipartFile ficher;
}
