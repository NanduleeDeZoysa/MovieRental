package com.example.movierental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResentWatchMoviesController {

    @GetMapping("/watchpage")
    public String home() {
        return "watch"; // No .html extension needed
    }

}
