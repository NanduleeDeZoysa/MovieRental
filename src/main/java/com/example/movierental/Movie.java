package com.example.movierental;

public class Movie {
    private Long id;
    private String title;
    private String genre;
    private int year;
    private double rating;
    private String posterUrl;

    public Movie() {}

    public Movie(Long id, String title, String genre, int year, double rating, String posterUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {

        this.title = title != null ? title.trim() : null;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre != null ? genre.trim() : null;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {

        this.rating = Math.max(0, Math.min(5, rating)); // Ensure rating is between 0-10
    }

    public String getPosterUrl() {
        return posterUrl;
    }
    public void setPosterUrl(String posterUrl) {

        this.posterUrl = posterUrl != null ? posterUrl.trim() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id != null && id.equals(movie.id);
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }
}