package com.example.movierental.services;

import com.example.movierental.model.MovieReview;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieReviewService {
    private final String REVIEWS_FILE = "reviews.json";
    private final ObjectMapper objectMapper;

    public MovieReviewService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        initializeFiles();
    }

    private void initializeFiles() {
        try {
            File reviewsFile = new File(REVIEWS_FILE);
            if (!reviewsFile.exists()) {
                objectMapper.writeValue(reviewsFile, new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing files", e);
        }
    }

    public void addReview(MovieReview review) {
        try {
            List<MovieReview> reviews = getAllReviews();
            reviews.add(review);
            objectMapper.writeValue(new File(REVIEWS_FILE), reviews);
        } catch (IOException e) {
            throw new RuntimeException("Error adding review", e);
        }
    }

    public List<MovieReview> getAllReviews() {
        try {
            return objectMapper.readValue(new File(REVIEWS_FILE),
                new TypeReference<List<MovieReview>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Map<String, Object> getMovieStats(String movieName) {
        List<MovieReview> movieReviews = getAllReviews().stream()
            .filter(r -> r.getMovieName().equalsIgnoreCase(movieName))
            .collect(Collectors.toList());

        double averageRating = movieReviews.stream()
            .mapToInt(MovieReview::getRating)
            .average()
            .orElse(0.0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("averageRating", Math.round(averageRating * 10.0) / 10.0);
        stats.put("numberOfReviews", movieReviews.size());
        stats.put("reviews", movieReviews);

        return stats;
    }

    public List<String> getAllMovieNames() {
        return getAllReviews().stream()
            .map(MovieReview::getMovieName)
            .distinct()
            .collect(Collectors.toList());
    }

    public void deleteById(int reviewId)
    {

    }
}