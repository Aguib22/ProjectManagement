package com.ProjectManagement.digitalis.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Aguibou sow
 * @date 2025-03-20 08:43
 * @package com.ProjectManagement.digitalis.dto
 * @project digitalis
 */
@Data
@RequiredArgsConstructor
public class UserTokenFmcDto {
    private Long userId;
    private String token;
}
