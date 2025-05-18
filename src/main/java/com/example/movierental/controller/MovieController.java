package com.example.movierental.controller;

import com.example.movierental.model.Movie;
import com.example.movierental.model.User;
import util.MovieManager;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        //shorting
        MovieManager.sortMoviesByRating();
            return MovieManager.getMovies();
    }
//add to move for move list
    @PostMapping("/watch")
    public String watchMovie(@RequestParam String title, HttpSession session) {
        //atribute acssese
        User user = (User) session.getAttribute("loggedInUser");
        Movie movie = MovieManager.getMovieByTitle(title);
        if (user != null && movie != null) {
            user.watchMovie(movie);
            return "Movie added to recent list!";
        }
        return "User not found or movie not found";
    }
//check to list
    @GetMapping("/watchlist")
    public List<String> getRecentMovies(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        List<String> result = new ArrayList<>();
        if (user != null) {
            for (int i = user.getRecentMovies().size() - 1; i >= 0; i--) {
                result.add(user.getRecentMovies().get(i).getTitle());
            }
        }
        return result;
    }
}
