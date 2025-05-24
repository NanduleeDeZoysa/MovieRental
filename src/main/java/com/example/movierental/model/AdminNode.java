package com.example.movierental.model;

public class AdminNode extends Admin {

    public AdminNode next;

    public AdminNode(String name, String email, String number, String role, String password) {
        super(name, email, number, role, password);
        this.next = null;
    }

}
