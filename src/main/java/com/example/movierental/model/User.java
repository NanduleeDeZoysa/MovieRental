package com.example.movierental.model;
import java.util.Stack;


public class User {
    protected String name;
    protected String email;
    protected String number;
    public String password;
    public String repeatPassword;
    private Stack<Movie> recentMovies = new Stack<>();



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
        this.password = "";
        this.number = "";
        this.password = "";
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
<<<<<<< HEAD
=======

    public void setNumber(String number) {
        this.number = number;
    }
>>>>>>> origin/main
}

