package com.example.movierental.model;

import java.time.LocalDateTime;

public class Review {
    private String movieName;
    private String review;
    private int rating;
    private LocalDateTime timestamp;

    public Review() {
    }

    public Review(String movieName, String review, int rating) {
        this.movieName = movieName;
        this.review = review;
        this.rating = rating;
        this.timestamp = LocalDateTime.now();
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
