package com.example.petshop.RequestTemplate;


public class AuthenticationRequest {
    private String credential;
    private String password;

    public AuthenticationRequest(String credential, String password){
        this.credential=credential;
        this.password=password;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}