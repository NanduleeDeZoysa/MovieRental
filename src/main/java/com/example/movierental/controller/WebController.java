package com.example.movierental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class WebController {

    @GetMapping("/add")
    public String addReview() {
        return "RatingAndReview";
    }

    @GetMapping("/view")
    public String viewReviews() {
        return "MovieReview";
    }
} 