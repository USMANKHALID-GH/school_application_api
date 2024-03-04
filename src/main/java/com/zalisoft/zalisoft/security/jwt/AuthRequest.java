package com.zalisoft.zalisoft.security.jwt;

import lombok.Data;

@Data
public class AuthRequest {


    private String email;
    private String password;

    @Override
    public String toString() {
        return "AuthRequest{" +
                "email='" + email + '\'' +
                '}';
    }
}