package com.example.movierental.services;

import com.example.movierental.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MovieService {

    private final String filePath = "movies.json";
    private List<Movie> movieList = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MovieService() {
        loadMovies();
    }

    private void loadMovies() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                movieList = objectMapper.readValue(file, new TypeReference<List<Movie>>() {});
            }
        } catch (IOException e) {
            System.out.println("Error loading movies: " + e.getMessage());
        }
    }

    private void saveMovies() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), movieList);
        } catch (IOException e) {
            System.out.println("Error saving movies: " + e.getMessage());
        }
    }

    // ✅ Only sorting by rating
    public List<Movie> getAllMoviesSortedByRating(boolean descending) {
        List<Movie> sortedList = new ArrayList<>(movieList);
        sortedList.sort(Comparator.comparingDouble(Movie::getRating));
        if (descending) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieList.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void saveMovie(Movie movie) {
        if (movie.getId() == null) {
            long newId = movieList.stream().mapToLong(Movie::getId).max().orElse(0) + 1;
            movie.setId(newId);
            movieList.add(movie);
        } else {
            deleteMovie(movie.getId());
            movieList.add(movie);
        }
        saveMovies();
    }

    public void deleteMovie(Long id) {
        movieList.removeIf(m -> m.getId().equals(id));
        saveMovies();
    }
}
