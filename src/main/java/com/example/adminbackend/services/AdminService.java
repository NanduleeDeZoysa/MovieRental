// 14/05/2025
//5.30

package com.example.adminbackend.services;
import com.example.adminbackend.model.movie;
import com.example.adminbackend.model.User;

import java.util.ArrayList;
import java.util.List;

interface AdminService
{
    int getMovieCount();
    int getUserCount();
    int getReviewCount();
    List<Movie> getMovies();
    Movie getMovie(int movieId);
    void addMovie(Movie movie);
    void deleteMovie(int movieId);
    List<User> getUsers();
    void promoteToAdmin(User user);
    void deleteUser(int userId);
    List<Review> getReview();
    void deleteReview(int movieId);
}

abstract class baseService
{
    protected static int serviceCounter = 0;
    public baseServise()
    {
       serviceCounter++;
    }
    public abstract int getMovieCount();

    public static int getServiceCounter()
    {
        return serviceCounter;
    }
}
class Movie {
    private int MovieId;
    private String Title;
    private String Description;
    private int rating;
    private int Year;

    public Movie(int MovieId, String Title, String Description, int rating, int Year) {
        this.MovieId = MovieId;
        this.Title = Title;
        this.Description = Description;
        this.rating = rating;
        this.Year = Year;
    }

    //getter
    public int getMovieId() {
        return MovieId;
    }

    public String getTitle() {
        return Title;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return Description;
    }

    public int getYear() {
        return Year;
    }
}
class User {
    private int UserId;
    private String UserName;
    private String UserEmail;
    private String userRole;

    // constructor
    public User(int UserId, String UserName, String UserEmail, String UserRole) {
        this.UserId = UserId;
        this.UserName = UserName;
        this.UserEmail = UserEmail;
        this.userRole = UserRole;
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserRole() {
        return userRole;
    }
}

class Review {
    private int ReviewId;
    private int movieId;
    private int UserId;
    private String ReviewText;
    private int Rating;

    public Review(int ReviewId, int movieId, int UserId, String ReviewText, int Rating) {
        this.ReviewId = ReviewId;
        this.movieId = movieId;
        this.UserId = UserId;
        this.ReviewText = ReviewText;
        this.Rating = Rating;

    }

    public int getReviewId() {
        return ReviewId;
    }

    public int getmovieId() {
        return movieId;
    }

    public int getUserId() {
        return UserId;
    }

    public String getReviewText() {
        return ReviewText;
    }

    public int getRating() {
        return Rating;
    }
}
class DataCollection {
    private List<Movie> movies = new ArrayList<Movie>();
    private List<User> users = new ArrayList<User>();
    private List<Review> reviews = new ArrayList<Review>();
    private int movieCount = 0;
    private int userCount = 0;
    private int reviewCount = 0;


    public void addMovie(Movie movie) {
        movies.add(movie);
        movieCount++;
    }

    public void addUser(User user) {
        users.add(user);
        userCount++;
    }

    public void addReview(Review review) {
        reviews.add(review);
        reviewCount++;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }

    public int getMovieCount() {
        return movieCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public Movie getMovie(int movieId) {
        return movies.stream().filter(x -> x.getMovieId() == movieId).findFirst().get();
    }

    public User getUser(int userId) {
        return users.stream().filter(x -> x.getUserId() == userId).findFirst().get();
    }

    public Review getReview(int reviewId) {
        return reviews.stream().filter(x -> x.getReviewId() == reviewId).findFirst().get();
    }

    public void removeMovie(int movieId) {
        movies.removeIf(x -> x.getMovieId() == movieId);
    }

    public void removeUser(int userId) {
        users.removeIf(x -> x.getUserId() == userId);
        userCount--;
    }

    public void removeReview(int ReviewId) {
        reviews.removeIf(x -> x.getUserId() == ReviewId);
        reviewCount--;
    }
}
class adminService extends baseService implements AdminService
{
    private DataCollection dataCollection;

    public adminService() {
        this(new DataCollection());
    }
    public adminService(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
    }

    @Override
    public int getMovieCount() {
        return dataCollection.getMovieCount();
    }
    @Override
    public int getUserCount() {
        return dataCollection.getUserCount();
    }
    @Override
    public int getReviewCount() {
        return dataCollection.getReviewCount();
    }
    @Override
    public List<Movie> getMovies() {
        return dataCollection.getMovie();
    }
    @Override
    public Movie getMovieById(int movieId) {
        return dataCollection.getMovieById(movieId);
    }
    @Override
    public void addMovie(Movie movie) {
        dataCollection.addMovie(movie);
    }
    @Override
    public void updateMovie(Movie movie)
    {
        Movie existingMovie = dataCollection.getMovieById(movie.getMovieId());
        if(existingMovie != null)
        {
            dataCollection.removeMovie(movie.getMovieId());
            dataCollection.addMovie(movie);
        }
    }
    @Override
    @Override
    public void deleteMovie(int id) {
        dataCollection.removeMovie(id);
    }

    @Override
    public List<User> getAllUsers() {
        return dataCollection.getUsers();
    }

    @Override
    public void promoteToAdmin(User user) {
        System.out.println("Promoting " + user.getUsername() + " to admin");
    }

    @Override
    public void deleteUser(int id) {
        dataCollection.removeUser(id);
    }

    @Override
    public List<Review> getAllReviews() {
        return dataCollection.getReviews();
    }

    @Override
    public void deleteReview(int id) {
        dataCollection.removeReview(id);
    }

    @Override
    public int getTotalCount() {
        return getMovieCount() + getUserCount() + getReviewCount();
    }
}

public class Main
{
    public static void main(String[] args) {
        AdminService adminService = new AdminService();
        adminService.addMovie(new Movie(1, "Inception", 2010));
        adminService.addUser(new User(1, "john_doe"));
        adminService.addReview(new Review(1, "Great movie", 5));
        System.out.println("Movie Count: " + adminService.getMovieCount());
        System.out.println("User Count: " + adminService.getUserCount());
        System.out.println("Review Count: " + adminService.getReviewCount());
        System.out.println("Total Count: " + adminService.getTotalCount());
    }
}











































