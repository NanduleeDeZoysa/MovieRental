package com.example.movierental.model;

public class UserNode extends User {
    public UserNode next;

    //Constructor
    public UserNode(String name, String email, String number, String password,String UserID,String repeatPassword) {
        super(name, email, number, password,UserID,repeatPassword);
        this.next = null ;
    }

    public UserNode(String name, String email, String number, String password)
    {
    }

    //Display Method
    public String toString() {
        return this.getName()+":"+this.getEmail()+":"+this.getNumber()+":"+this.getPassword();
    }

    public UserNode getNext()
    {
        return next;
    }
}
