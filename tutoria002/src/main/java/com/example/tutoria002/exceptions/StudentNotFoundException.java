package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends BusinessException {
    public StudentNotFoundException(int id) {
        super("El estudiante con id " + id + " no existe", HttpStatus.NOT_FOUND);
    }
}