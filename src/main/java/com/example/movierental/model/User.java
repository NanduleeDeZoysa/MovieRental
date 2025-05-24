package com.example.movierental.model;
import java.util.Stack;


public class User {
    protected String name;
    protected String email;
    protected String number;
    public String role;
    public String password;

    public String repeatPassword;
    public MyStack recentMovies = new MyStack(5);


    public User( String name, String email, String number, String role, String password) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.role = role;
        this.password = password;
        this.repeatPassword = password;
    }

    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.number = "";
        this.role = "";
        this.password = "";
    }

    //pass a Movie object when calling it.
    public void watchMovie(Movie movie) {
        recentMovies.push(movie); // use array-based push
    }

    //Returns the stack of movies user has watched.
    public MyStack getRecentMovies() {
        return recentMovies;
    }

    //Allows assigning a new MyStack to this user
    public void setRecentMovies(MyStack movies) {
        this.recentMovies = movies;
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
    public String getRole(){return role;}

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
    public void setRole(String role) {this.role = role;}
}

