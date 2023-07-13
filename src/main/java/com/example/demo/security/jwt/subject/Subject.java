package com.example.demo.security.jwt.subject;

import lombok.Getter;

@Getter
public class Subject {

    final private Long Id;

    final private String email;

    final private String types;

    public Subject(Long id, String email, String types) {
        Id = id;
        this.email = email;
        this.types = types;
    }
    public static Subject accessToken(Long accountId, String email) {
        return new Subject(accountId, email, "accessToken");
    }

    public static Subject refreshToken(Long accountId, String email) {
        return new Subject(accountId, email, "refreshToken");
    }
}
