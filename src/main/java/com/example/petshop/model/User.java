package com.example.petshop.model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name="username")
    String username;
    @Column(name="email_address")
    String emailAddress;
    @Column(name="password")
    String password;

    @Column(name="role")
    String role;

    @Column(name="active")
    Boolean active;

    public User(String username,String emailAddress, String password,String role,Boolean active){
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
