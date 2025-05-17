package com.example.movierental.model;

public class UserNode extends User {
    public UserNode next;

    //Constructor
    public UserNode(String name, String email, String number, String password) {
        super(name, email, number, password);
        this.next = null ;
    }

    //Display Method
    public String toString() {
        return this.getName()+":"+this.getEmail()+":"+this.getNumber()+":"+this.getPassword();
    }

}
