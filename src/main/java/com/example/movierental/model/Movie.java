package com.example.movierental.model;

public class Movie {
<<<<<<< HEAD
    private Long id;
    private String title;
    private String genre;
    private int year;
    private double rating;

    public Movie() {}

    public Movie(Long id, String title, String genre, int year, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
=======
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
>>>>>>> origin/main
