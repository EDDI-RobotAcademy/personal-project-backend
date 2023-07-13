package com.example.demo.security.jwt.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    public BadRequestException (String message) {
        super(message);
    }
}
