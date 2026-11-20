package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateFacultyCodeException extends BusinessException {
    public DuplicateFacultyCodeException(String code) {
        super("Ya existe una facultad con el código " + code, HttpStatus.CONFLICT);
    }
}