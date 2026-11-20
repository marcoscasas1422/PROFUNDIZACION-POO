package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateStudentEmailException extends BusinessException {
    public DuplicateStudentEmailException(String email) {
        super("Ya existe un estudiante con el email " + email, HttpStatus.CONFLICT);
    }
}