package com.example.movierental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
//import javax.validation.Valid;
import org.springframework.ui.Model;
import com.example.movierental.model.Review;
import com.example.movierental.service.ReviewService;
import com.example.movierental.service.MovieService;

import java.io.IOException;

@Controller
public class ReviewController {
    private final ReviewService reviewService;
    private final MovieService movieService;

    public ReviewController(ReviewService reviewService, MovieService movieService) {
        this.reviewService = reviewService;
        this.movieService = movieService;
    }

    @GetMapping("/review/new")
    public String showReviewForm(@RequestParam String movie, Model model) {
        model.addAttribute("movie", movieService.getMovie(movie));
        model.addAttribute("review", new Review());
        return "review";
    }

    @GetMapping("/reviews")
    public String showReviews(@RequestParam(required = false) String movie, Model model) {
        if (movie != null) {
            model.addAttribute("movie", movieService.getMovie(movie));
            try {
                model.addAttribute("reviews", reviewService.getMovieReviews(movie));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                model.addAttribute("averageRating", reviewService.getAverageRating(movie));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                model.addAttribute("reviews", reviewService.getAllReviews());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "movie-reviews";
    }

    @PostMapping("/review")
    public String submitReview( @ModelAttribute Review review, BindingResult result) {
        if (result.hasErrors()) {
            return "review";
        }
        try {
            reviewService.addReview(review);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/reviews?movie=" + review.getMovieTitle();
    }
}