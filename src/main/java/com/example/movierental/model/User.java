package com.example.movierental.model;

public class User {
    protected String name;
    protected String email;
    protected String number;
    public String password;
    public String repeatPassword;


    public User( String name, String email, String number, String password) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.password = password;
        this.repeatPassword = password;
    }

    public User() {
        this.name = "";
        this.email = "";
        this.number = "";
        this.password = "";
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getNumber() {
        return number;
    }
    public String getPassword() {
        return password;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

