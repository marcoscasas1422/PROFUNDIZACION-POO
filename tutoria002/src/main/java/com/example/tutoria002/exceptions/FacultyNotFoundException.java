package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class FacultyNotFoundException extends BusinessException {
    public FacultyNotFoundException(int id) {
        super("La facultad con id " + id + " no existe", HttpStatus.NOT_FOUND);
    }
}