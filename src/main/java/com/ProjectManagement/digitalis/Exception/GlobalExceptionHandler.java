package com.ProjectManagement.digitalis.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GtError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String onGtError(GtError ex ){
        return ex.getMessage();
    }

    @ExceptionHandler(ProjetError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String onProjetError(ProjetError ex ){
        return ex.getMessage();
    }

    @ExceptionHandler(ReunionError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String onReunionError(ReunionError ex){
        return ex.getMessage();
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String onRuntime(RuntimeException ex){
        return ex.getMessage();
    }
}
