package com.example.movierental.model;

public class Movie {
    private int id;
    private String title;
    private double rating;
    private String review;

    public Movie() {}

    public Movie(String title, double rating, String review) {
        this.title = title;
        this.rating = rating;
        this.review = review;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
}