package com.example.adminbackend.services;

import com.example.adminbackend.model.Movie;
import com.example.adminbackend.model.Review;
import com.example.adminbackend.model.User;
import com.example.adminbackend.services.MovieService;
import com.example.adminbackend.services.UserServices;
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
    private ReviewService reviewService;

    public int getMovieCount()
    {
        return (int) movieService.count();
    }
    public int getUserCount()
    {
        return (int) userServices.count();
    }
    public int getReviewCount()
    {
        return (int) reviewService.count();
    }

    public List<Movie> getMovies()
    {
        return movieService.findAll();
    }
    public Optional<Movie> getMovieById(int movieId)
    {
        return movieService.findById(movieId);
    }
    public void addMovie(Movie movie)
    {
        movieService.saveMovie(movie);
    }

    public void updateMovie(Movie movie) {
        Optional<Movie> existingMovie = movieService.findById(movie.getMovieId());
        if (existingMovie.isPresent()) {
            Movie updatedMovie = existingMovie.get();
            updatedMovie.setTitle(movie.getTitle());
            updatedMovie.setDescription(movie.getDescription());
            updatedMovie.setRating(movie.getRating());
            updatedMovie.setYear(movie.getYear());
            updatedMovie.setGenre(movie.getGenre());
        }
    }
    public void deleteMovie(int movieId)
    {
        movieService.getMovieById(movieId);
    }
    public List<User> getUsers()
    {
        return userServices.findAll();
    }
    public void promoteToAdmin(User user) {
        Optional<User> exitingUser = userServices.findById(user.getUserId());
        if (exitingUser.isPresent()) {
            exitingUser.get().setUserRole("ADMIN");
            userServices.saveUser(userServices.readUsers());
        }
        else {
            System.out.println("User not found");
        }
    }
    public void deleUser(int userID)
    {
        userServices.deleteUser(userServices.readUsers(),String.valueOf(userID));
        System.out.println("User deleted");
    }
    public List<Review> getReview()
    {
        return reviewService.findAll();
    }
    public void deleteReviews(int reviewId)
    {
        reviewService.deleteByID(reviewId);
    }
    public int getTtalCount()
    {
        return getMovieCount()+getUserCount()+getReviewCount();
    }


    public void deleteUser(int id) {
    }

    public Object getTotalCount() {
    }
}






































