package com.example.movierental.service;

import com.example.movierental.model.Review;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private static final String REVIEWS_FILE = "reviews.txt";

    // Add a new review
    public synchronized void addReview(Review review) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REVIEWS_FILE, true))) {
            writer.write(review.toCsvString());
            writer.newLine();
        }
    }

    // Get all reviews for a specific movie
    public List<Review> getMovieReviews(String movieTitle) throws IOException {
        List<Review> reviews = getAllReviews();
        return reviews.stream()
                .filter(r -> r.getMovieTitle().equalsIgnoreCase(movieTitle))
                .collect(Collectors.toList());
    }

    // Get all reviews
    public List<Review> getAllReviews() throws IOException {
        List<Review> reviews = new ArrayList<>();
        File file = new File(REVIEWS_FILE);

        if (!file.exists()) {
            file.createNewFile();
            return reviews;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    reviews.add(Review.fromCsvString(line));
                }
            }
        }
        return reviews;
    }

    // Calculate average rating for a movie
    public double getAverageRating(String movieTitle) throws IOException {
        List<Review> movieReviews = getMovieReviews(movieTitle);
        if (movieReviews.isEmpty()) {
            return 0.0;
        }
        return movieReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}