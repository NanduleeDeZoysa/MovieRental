package com.example.movierental.controller;

import com.example.movierental.model.MovieReview;
import com.example.movierental.service.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class MovieReviewController {

    @Autowired
    private MovieReviewService movieReviewService;

    @PostMapping
    public ResponseEntity<Void> addReview(@RequestBody MovieReview review) {
        movieReviewService.addReview(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MovieReview>> getAllReviews() {
        return ResponseEntity.ok(movieReviewService.getAllReviews());
    }

    @GetMapping("/movies")
    public ResponseEntity<List<String>> getAllMovieNames() {
        return ResponseEntity.ok(movieReviewService.getAllMovieNames());
    }

    @GetMapping("/movie/{movieName}")
    public ResponseEntity<Map<String, Object>> getMovieStats(@PathVariable String movieName) {
        return ResponseEntity.ok(movieReviewService.getMovieStats(movieName));
    }
} 