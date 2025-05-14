package com.example.adminbackend.services;

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
    private List<movie> movieList = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MovieService() {
        loadMovies();  // Load movies from file when the service is initialized
    }

    private void loadMovies() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                movieList = objectMapper.readValue(file, new TypeReference<List<movie>>() {});
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

    public List<Movie> getAllMovies(String sortBy) {
        List<Movie> sortedList = new ArrayList<>(movieList);

        // Sort movies based on the sortBy parameter
        bubbleSort(sortedList, sortBy);
        return sortedList;
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieList.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void saveMovie(Movie movie) {
        if (movie.getId() == null) {
            // Generate new ID for the movie
            long newId = movieList.stream().mapToLong(Movie::getId).max().orElse(0) + 1;
            movie.setId(newId);
            movieList.add(movie);
        } else {
            // Update existing movie
            deleteMovie(movie.getId());
            movieList.add(movie);
        }
        saveMovies();  // Save updated movie list to file
    }

    public void deleteMovie(Long id) {
        movieList.removeIf(m -> m.getId().equals(id));
        saveMovies();  // Save updated movie list to file after deletion
    }

    private void bubbleSort(List<Movie> list, String sortBy) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.size() - i; j++) {
                boolean shouldSwap = false;
                Movie m1 = list.get(j - 1);
                Movie m2 = list.get(j);

                switch (sortBy.toLowerCase()) {
                    case "title":
                        shouldSwap = m1.getTitle().compareToIgnoreCase(m2.getTitle()) > 0;
                        break;
                    case "genre":
                        shouldSwap = m1.getGenre().compareToIgnoreCase(m2.getGenre()) > 0;
                        break;
                    case "year":
                        shouldSwap = m1.getYear() > m2.getYear();
                        break;
                    case "rating": // Add sorting by rating
                        shouldSwap = m1.getRating() > m2.getRating();
                        break;
                }

                if (shouldSwap) {
                    Collections.swap(list, j - 1, j);
                }
            }
        }
    }
}
