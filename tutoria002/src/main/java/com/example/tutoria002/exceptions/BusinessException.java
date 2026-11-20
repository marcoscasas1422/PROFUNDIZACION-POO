package com.example.tutoria002.exceptions;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final HttpStatus status;

    protected BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}