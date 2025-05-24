package com.example.movierental.model;

class Admin extends User {

    public Admin(String name, String email, String number, String role, String password) {
        super(name, email, number, role, password);
    }

    public Admin() {
        super();
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getNumber() {
        return number;
    }
    public String getRole() {
        return role;
    }


    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setRole(String role) {
        this.role = role;
    }


}

