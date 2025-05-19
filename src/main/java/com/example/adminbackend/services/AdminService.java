package com.example.movierental.services;

import com.example.movierental.model.Movie;
import com.example.movierental.model.MovieReview;
import com.example.movierental.model.User;
import com.example.movierental.model.UserList;
import com.example.movierental.services.MovieService;
import com.example.movierental.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    @Autowired
    private UserServices userServices;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieReviewService reviewService;

    public int getMovieCount()
    {
        return (int) movieService.count();
    } // movieService
    public int getUserCount()
    {
        return userServices.readUsers().find();
    }
    public int getReviewCount()
    {
        return reviewService.getAllReviews().size();
    }

    public List<Movie> getAllMovies(String sortBy)
    {
        return movieService.getAllMovies(sortBy);
    }
    // movieService
    public Optional<Movie> getMovieById(long id)
    {
        return movieService.getMovieById(id);
    }
    //movieService
    public void addMovie(Movie movie)
    {
        movieService.saveMovie(movie);
    }

    public void updateMovie(Movie movie) {
        Optional<Movie> existingMovie = movieService.getMovieById(movie.getId());
        if (existingMovie.isPresent()) {
            Movie updatedMovie = existingMovie.get();
            updatedMovie.setTitle(movie.getTitle());
            updatedMovie.setDescription(movie.getDescription());
            updatedMovie.setRating(movie.getRating());
            updatedMovie.setYear(movie.getYear());
            updatedMovie.setGenre(movie.getGenre());
            movieService.saveMovie(updatedMovie);
            // save the updated movie
        }
    }
    public void deleteMovie(long movieId)
    {
        movieService.getMovieById(movieId);
    }
    public List<User> getUsers()
    {
        return userServices.count();
    }
    public void promoteToAdmin(User user) {
        Optional<User> exitingUser = userServices.getfindById(user.getUserID());
        if (exitingUser.isPresent()) {
            exitingUser.get().setUserRole("ADMIN");
            userServices.saveUser(new UserList());
        }
        else {
            System.out.println("User not found");
        }
    }
    public void deleteUser(long userID)
    {
        userServices.deleteUser(userID);
        System.out.println("User deleted");
    }
    public List<MovieReview> getReview()
    {
        return reviewService.getAllReviews();
    }
    public void deleteReviews(int reviewId)
    {
        reviewService.deleteById(reviewId);
    }

    public Object getTotalCount()
    {
        int movieCount = getMovieCount();
        int userCount = getUserCount();
        int reviewCount = getReviewCount();
        return movieCount+userCount+reviewCount;

    }
}






































