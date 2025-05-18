package com.example.movierental.controller;

import com.example.movierental.model.Movie;
import com.example.movierental.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class MovieListController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movieList")
    public String movieList(Model model) {
        model.addAttribute("movies", movieService.getAllMovies("rating"));
        return "movieList";  //  matches movieList.html

    }

    // === LIST MOVIES ===
    @GetMapping("/movies")
    public String getAllMovies(
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            Model model
    ) {
        List<Movie> movies = movieService.getAllMovies("rating");

        // Bubble sort by rating
        if ("rating".equalsIgnoreCase(sortBy)) {
            movies = bubbleSortMoviesByRating(movies);
        }

        model.addAttribute("movies", movies);
        return "movieList";  // Matches movieList.html
    }

    // === ADD MOVIE FORM ===
    @GetMapping("/movies/add")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movieForm";  // This matches movieForm.html
    }

    // === HANDLE ADD MOVIE ===
    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/movies";
    }

    // === EDIT MOVIE FORM ===
    @GetMapping("/movies/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id).orElseThrow();
        model.addAttribute("movie", movie);
        return "movieEdit";  // Matches movieEdit.html
    }

    // === HANDLE EDIT MOVIE ===
    @PostMapping("/movies/edit/{id}")
    public String editMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movie.setId(id);
        movieService.saveMovie(movie);
        return "redirect:/movies";
    }

    // === DELETE MOVIE ===
    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    // === BUBBLE SORT BY RATING ===
    private List<Movie> bubbleSortMoviesByRating(List<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies.size() - 1 - i; j++) {
                if (movies.get(j).getRating() < movies.get(j + 1).getRating()) {
                    Collections.swap(movies, j, j + 1);
                }
            }
        }
        return movies;
    }
}
