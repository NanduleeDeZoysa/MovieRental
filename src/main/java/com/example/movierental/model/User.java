package com.example.movierental.model;

import java.util.Stack;
import model.Movie;

public class User {
    protected String name;
    protected String email;
    public String password;
    public String repeatPassword;
    private Stack<Movie> recentMovies = new Stack<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.repeatPassword = password;
    }

    public User() {
        this.name = "";
        this.email = "";
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