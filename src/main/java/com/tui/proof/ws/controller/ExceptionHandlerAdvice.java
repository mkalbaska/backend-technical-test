package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.validation.ManualValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlerAdvice  {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return getErrors(ex.getBindingResult());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ManualValidationException.class)
    public Map<String, String> handleValidationExceptions(ManualValidationException ex) {
        return getErrors(ex.getErrors());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleValidationExceptions(NoSuchElementException ex) {
        return ex.getMessage();
    }

    private Map<String, String> getErrors(Errors bindingResult) {
        Map<String, String> result = new HashMap<>();

        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            result.put(fieldName, errorMessage);
        });
        return result;
    }
}
