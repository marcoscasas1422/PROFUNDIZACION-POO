package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateTeacherEmailException extends BusinessException {
    public DuplicateTeacherEmailException(String email) {
        super("Ya existe un profesor con el email " + email, HttpStatus.CONFLICT);
    }
}