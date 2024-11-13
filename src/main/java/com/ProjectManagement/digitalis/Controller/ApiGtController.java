package com.ProjectManagement.digitalis.Controller;

import com.ProjectManagement.digitalis.Services.GtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiGtController {

    @Autowired
    private GtServices gtServices;





}
