package com.ProjectManagement.digitalis.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DirectionDto {

    private String nomDirection;
    private Long organisation;
    private List<Long> userServices;
}

