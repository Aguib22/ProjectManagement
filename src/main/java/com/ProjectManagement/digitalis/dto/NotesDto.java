package com.ProjectManagement.digitalis.dto;

import com.ProjectManagement.digitalis.entitie.Tache;
import com.ProjectManagement.digitalis.entitie.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-03-16 14:32
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */
@Data
@RequiredArgsConstructor
public class NotesDto {

    private String title;
    private String content;
    private String imageUrl;
    private List<Tache> taches = new ArrayList<>();

    private Long user;

}
