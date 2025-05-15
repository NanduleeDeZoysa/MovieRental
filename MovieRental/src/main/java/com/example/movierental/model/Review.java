package com.example.movierental.model;

public class Review {
    private String userId;
    private String movieTitle;
    private String comment;
    private int rating;
    private boolean isAnonymous;
    private String reviewDate;

    // No-args constructor for Spring MVC
    public Review() {
        this.reviewDate = java.time.LocalDateTime.now().toString();
    }

    // Constructor
    public Review(String userId, String movieTitle, String comment, int rating, boolean isAnonymous) {
        this.userId = userId;
        this.movieTitle = movieTitle;
        this.comment = comment;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
        this.reviewDate = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public boolean isAnonymous() { return isAnonymous; }
    public void setAnonymous(boolean anonymous) { isAnonymous = anonymous; }

    public String getReviewDate() { return reviewDate; }
    public void setReviewDate(String reviewDate) { this.reviewDate = reviewDate; }

    // Method to convert Review to CSV format for storage
    public String toCsvString() {
        return String.format("%s,%s,%s,%d,%b,%s",
                userId, movieTitle, comment.replace(",", ";;"),
                rating, isAnonymous, reviewDate);
    }

    // Method to create Review from CSV line
    public static Review fromCsvString(String line) {
        String[] parts = line.split(",");
        Review review = new Review(
                parts[0], // userId
                parts[1], // movieTitle
                parts[2].replace(";;", ","), // comment
                Integer.parseInt(parts[3]), // rating
                Boolean.parseBoolean(parts[4]) // isAnonymous
        );
        review.setReviewDate(parts[5]); // reviewDate
        return review;
    }
}