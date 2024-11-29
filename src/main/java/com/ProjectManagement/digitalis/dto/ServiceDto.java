package com.ProjectManagement.digitalis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ServiceDto {

    private String nomUserService;
    private Long direction;
    private List<Long> users;
}
