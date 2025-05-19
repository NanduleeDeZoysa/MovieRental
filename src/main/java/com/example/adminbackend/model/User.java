package com.example.movierental.model;
import java.util.Stack;


public class User {
    protected String UserID;
    protected String name;
    protected String email;
    protected String number;
    public String password;
    public String repeatPassword;
    private Stack<Movie> recentMovies = new Stack<>();
    private String UserRole;


    public User(String name, String email, String number, String password, String UserID, String UserRole) {
        this.name = name;
        this.UserID = UserID;
        this.UserRole = UserRole;
        this.email = email;
        this.number = number;
        this.password = password;
        this.repeatPassword = password;
    }

    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.number = "";
        this.password = "";
        this.UserID = "";
        this.UserRole = "";
    }

    public User(String name, String email, String number, String password, String UserID, String UserRole, String repeatPassword)
    {
    }

    public void watchMovie(Movie movie) {
        if (recentMovies.size() == 5) {
            recentMovies.remove(0);
        }
        recentMovies.push(movie);
    }

    public Stack<Movie> getRecentMovies() {
        return recentMovies;
    }

    public void setRecentMovies(Stack<Movie> movies) {
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

    public String getUserID() {
        return UserID;
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

    public void setUserID(String userID) {
        UserID = UserID;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
}

