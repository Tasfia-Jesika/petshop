package com.example.petshop.ResponseTemplate;

public class AuthenticationResponse {
    private String jwt;
    private String fullName;
    private Integer userId;

    public AuthenticationResponse(String jwt, String fullName, Integer userId){
        this.jwt=jwt;
        this.fullName = fullName;
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getUserId() {
        return userId;
    }
}

