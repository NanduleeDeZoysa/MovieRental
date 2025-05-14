package com.example.adminbackend.model;

public class User {
    protected String name;
    protected String email;
    public String password;
    public String repeatPassword;

    // constructor
    public User( String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.repeatPassword = password;
    }
    // default constructor
    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
