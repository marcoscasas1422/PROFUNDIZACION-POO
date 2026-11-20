package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;

public class FacultyHasTeachersException extends BusinessException {
    public FacultyHasTeachersException(int id) {
        super("No se puede eliminar la facultad " + id + " porque tiene docentes", HttpStatus.CONFLICT);
    }
}