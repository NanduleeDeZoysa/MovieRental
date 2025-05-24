package com.example.movierental.model;

public class UserNode extends User {
    public UserNode next;

    //Constructor
    public UserNode(String name, String email, String number, String role, String password) {
        super(name, email, number, role, password);
        this.next = null ;
    }

    //Display Method
    public String toString() {
        return this.getName()+":"+this.getEmail()+":"+this.getNumber()+":" +this.getRole()+":"+this.getPassword();
    }

}
