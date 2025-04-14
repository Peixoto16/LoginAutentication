package com.registro.Register.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DomainException extends ResponseStatusException {

    public DomainException(String message, HttpStatus status) {

        super(status, message);
    }

    public DomainException(String message) {

        super(HttpStatus.BAD_REQUEST, message);
    }

}