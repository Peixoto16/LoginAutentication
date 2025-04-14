package com.registro.Register.controller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdviceApp {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ReturnError> handlerResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(new ReturnError(ex), ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ReturnError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> validationMessages = extractValidationMessages(ex);

        String errorMessage = validationMessages.isEmpty()
                ? "Erro de validação desconhecido"
                : String.join(", ", validationMessages);

        ReturnError returnError = new ReturnError(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage));
        return new ResponseEntity<>(returnError, HttpStatus.BAD_REQUEST);
    }
    private List<String> extractValidationMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Campo '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
    }

}
