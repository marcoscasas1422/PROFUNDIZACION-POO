package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class TeacherNotFoundException extends BusinessException {
    public TeacherNotFoundException(int id) {
        super("El profesor con id " + id + " no existe", HttpStatus.NOT_FOUND);
    }
}