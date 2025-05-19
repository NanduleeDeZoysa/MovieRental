package com.example.movierental.controller;

import com.example.movierental.model.MovieReview;
import com.example.movierental.services.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MovieReviewController {

    @Autowired
    private MovieReviewService movieReviewService;

    // REST API Endpoints
    @PostMapping("/api/reviews")
    @ResponseBody
    public ResponseEntity<Void> addReview(@RequestBody MovieReview review) {
        movieReviewService.addReview(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/reviews")
    @ResponseBody
    public ResponseEntity<List<MovieReview>> getAllReviews() {
        return ResponseEntity.ok(movieReviewService.getAllReviews());
    }

    @GetMapping("/api/reviews/movies")
    @ResponseBody
    public ResponseEntity<List<String>> getAllMovieNames() {
        return ResponseEntity.ok(movieReviewService.getAllMovieNames());
    }

    @GetMapping("/api/reviews/movie/{movieName}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getMovieStats(@PathVariable String movieName) {
        return ResponseEntity.ok(movieReviewService.getMovieStats(movieName));
    }

    // View Rendering Endpoints
    @GetMapping("/reviews/add")
    public String addReview() {
        return "RatingAndReview";
    }

    @GetMapping("/reviews/view")
    public String viewReviews() {
        return "MovieReview";
    }
}
