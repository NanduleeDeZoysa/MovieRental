package com.example.movierental;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private String password;
    private Set<Long> watchlist = new HashSet<>();


    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getWatchlist() {
        return new HashSet<>(watchlist); // Return a copy for immutability
    }

    // Watchlist management methods
    public void addToWatchlist(Long movieId) {
        if (movieId != null) {
            watchlist.add(movieId);
        }
    }

    public void removeFromWatchlist(Long movieId) {
        if (movieId != null) {
            watchlist.remove(movieId);
        }
    }

    public boolean isInWatchlist(Long movieId) {
        return movieId != null && watchlist.contains(movieId);
    }

    public void clearWatchlist() {
        watchlist.clear();
    }

    public int getWatchlistCount() {
        return watchlist.size();
    }

    // Other user methods as needed
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", watchlistSize=" + watchlist.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}