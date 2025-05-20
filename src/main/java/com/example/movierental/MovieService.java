package com.example.movierental;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final String DEFAULT_POSTER_URL = "https://via.placeholder.com/300x450?text=No+Poster";
    private final String filePath = "movies.json";
    private List<Movie> movieList = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Deque<Movie> recentlyWatched = new ArrayDeque<>(5);
    private final Set<Long> watchlist = ConcurrentHashMap.newKeySet();
    private final Map<String, List<Movie>> genreCache = new ConcurrentHashMap<>();

    public MovieService() {
        loadMovies();
        GenreCache();
    }


    private synchronized void loadMovies() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                movieList = objectMapper.readValue(file, new TypeReference<List<Movie>>() {});
                // all movies have poster URLs
                movieList.forEach(movie -> {
                    if (movie.getPosterUrl() == null || movie.getPosterUrl().isEmpty()) {
                        movie.setPosterUrl(DEFAULT_POSTER_URL);
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Error loading movies: " + e.getMessage());
            throw new RuntimeException("Failed to load movies data", e);
        }
    }

    private synchronized void saveMovies() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), movieList);
            GenreCache(); // Refresh cache after changes
        } catch (IOException e) {
            System.err.println("Error saving movies: " + e.getMessage());
            throw new RuntimeException("Failed to save movies data", e);
        }
    }



    private void GenreCache() {
        genreCache.clear();
        movieList.stream()
                .map(Movie::getGenre)
                .distinct()
                .forEach(genre -> genreCache.put(genre, getMoviesByGenre(genre)));
    }

    public List<Movie> getAllMovies(String sortBy) {
        List<Movie> sortedList = new ArrayList<>(movieList);
        if (sortBy != null && !sortBy.isEmpty()) {
            sortMovies(sortedList, sortBy);
        }
        return Collections.unmodifiableList(sortedList);
    }

    public List<Movie> getTopMovies(int count) {
        if (count <= 0) return Collections.emptyList();

        return movieList.stream()
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Optional<Movie> getMovieById(Long id) {
        if (id == null) return Optional.empty();
        return movieList.stream()
                .filter(m -> id.equals(m.getId()))
                .findFirst();
    }

    public synchronized void saveMovie(Movie movie) {
        if (movie == null) throw new IllegalArgumentException("Movie cannot be null");

        if (movie.getPosterUrl() == null || movie.getPosterUrl().isEmpty()) {
            movie.setPosterUrl(DEFAULT_POSTER_URL);
        }

        if (movie.getId() == null) {
            long newId = movieList.stream()
                    .mapToLong(Movie::getId)
                    .max()
                    .orElse(0) + 1;
            movie.setId(newId);
            movieList.add(movie);
        } else {
            deleteMovie(movie.getId());
            movieList.add(movie);
        }
        saveMovies();
    }

    public synchronized void deleteMovie(Long id) {
        if (id == null) return;

        boolean removed = movieList.removeIf(m -> id.equals(m.getId()));
        if (removed) {
            saveMovies();
            watchlist.remove(id);
            recentlyWatched.removeIf(m -> id.equals(m.getId()));
        }
    }







    public List<Movie> searchMovies(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String searchQuery = query.toLowerCase();
        return movieList.stream()
                .filter(movie -> IgnoreCase(movie.getTitle(), searchQuery) ||
                        IgnoreCase(movie.getGenre(), searchQuery))
                .collect(Collectors.toList());
    }

    private List<Movie> getMoviesByGenre(String genre) {
        return movieList.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    private void sortMovies(List<Movie> movies, String sortBy) {
        Comparator<Movie> comparator = switch (sortBy.toLowerCase()) {
            case "title" -> Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "genre" -> Comparator.comparing(Movie::getGenre, String.CASE_INSENSITIVE_ORDER);
            case "year" -> Comparator.comparingInt(Movie::getYear);
            case "rating" -> Comparator.comparingDouble(Movie::getRating).reversed();
            default -> throw new IllegalArgumentException("Invalid sort criteria: " + sortBy);
        };
        movies.sort(comparator);
    }

    private boolean IgnoreCase(String source, String search) {
        return source != null && source.toLowerCase().contains(search);
    }

    // Watchlist management methods
    public boolean isInWatchlist(Long movieId) {
        return watchlist.contains(movieId);
    }

    public synchronized void addToWatchlist(Long movieId) {
        if (movieId != null) {
            watchlist.add(movieId);
        }
    }

    public synchronized void removeFromWatchlist(Long movieId) {
        if (movieId != null) {
            watchlist.remove(movieId);
        }
    }

    public Set<Long> getWatchlist() {
        return Collections.unmodifiableSet(watchlist);
    }
}