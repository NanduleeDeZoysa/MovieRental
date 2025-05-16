package util;

import com.example.movierental.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    private static final List<Movie> movieList = new ArrayList<>();
    private static int idCounter = 1;

    public static void addMovie(Movie movie) {
        movie.setId(idCounter++);
        movieList.add(movie);
    }

    public static List<Movie> getMovies() {
        return movieList;
    }

    public static Movie getMovieById(int id) {
        for (Movie movie : movieList) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    public static Movie getMovieByTitle(String title) {
        for (Movie movie : movieList) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }

    public static void updateMovie(Movie updatedMovie) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId() == updatedMovie.getId()) {
                movieList.set(i, updatedMovie);
                return;
            }
        }
    }

    public static void deleteMovieById(int id) {
        movieList.removeIf(movie -> movie.getId() == id);
    }

    public static void sortMoviesByRating() {
        int n = movieList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (movieList.get(j).getRating() < movieList.get(j + 1).getRating()) {
                    Movie temp = movieList.get(j);
                    movieList.set(j, movieList.get(j + 1));
                    movieList.set(j + 1, temp);
                }
            }
        }
    }
}