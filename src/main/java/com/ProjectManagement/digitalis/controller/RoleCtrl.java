package com.ProjectManagement.digitalis.controller;

import com.ProjectManagement.digitalis.entitie.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aguibou sow
 * @date 2025-01-13 15:11
 * @package com.ProjectManagement.digitalis.controller
 * @project digitalis
 */
@RestController
@RequestMapping("/api/roles")
public class RoleCtrl {

    @GetMapping("")
    public List<Role> getAllRols(){
        return Arrays.asList(Role.values());
    }
}
