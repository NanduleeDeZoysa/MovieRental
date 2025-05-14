
// 13/05/2025
// 10.30

package com.example.adminbackend.controller;
import com.example.adminbackend.model.movie;
import com.example.adminbackend.services.Movie;
import com.example.adminbackend.services.MovieService;

import java.util.ArrayList;
import java.util.List;

import static BaseAdmin.getAdminCount;

//interface for admin operation
interface AdminOperations
{
    void dashboard();
    void promoteUser();
    void deleteUser();
}

        abstract  class BaseAdmin
        {
            protected String adminID;
            protected String adminPassword;
            protected static int adminCount = 0;

            public BaseAdmin(String adminID, String adminPassword)
            {
                this.adminID = adminID;
                this.adminPassword = adminPassword;
                adminCount++;
            }
            public abstract void manageMovies();
            public static int getAdminCount()
            {
                return adminCount;
            }
        }
        // create the movie class
        class Movies {
            private final String MovieID;
            private final String Title;
            private final String Description;
            private final int Year;
            private String genre;


            // constrotor
            public Movies(String MovieID,String Title,String Description,int Year,String genre)
            {
                this.MovieID = MovieID;
                this.Title = Title;
                this.Description = Description;
                this.Year = Year;
                this.genre = genre;
            }
            // getter
            public String getMovieID()
            {
                return MovieID;
            }
            public String geTitle()
            {
                return Title;
            }
            public String getDescription()
            {
                return Description;
            }
            public int getYear()
            {
                return Year;
            }
        }
        class Reviews
        {
            private String MovieID;
            private String Title;
            private String genre;
            private int  Rating;
            private long reviewdate;

            // create the constructor
            public Reviews(String MovieID,String Title,String Description,int Year,String genre,int Rating,long reviewdate)
            {
                this.MovieID = MovieID;
                this.Title = Title;
                this.genre = genre;
                this.Rating = Rating;
                this.reviewdate = reviewdate;
            }

            // getters
            public String getMovieID()
            {
                return MovieID;
            }
            public String getTitle()
            {
                return Title;
            }
            public String getgenre()
            {
                return genre;
            }
            public int getRating()
            {
                return Rating;
            }
            public long getReviewdate()
            {
                return reviewdate;
            }

        }

        class MovieCollection {
            private List<Movies> movies = new ArrayList<>();
            {
                void addMovie(Movie)
                    {
                            movies.add(movie);// Correct way to add the movie
                    }
            }

        }
        public void addMovie(movie Movie)
        {
            movie.setTitle(Movie);
        }
        public List<Movies> getMovies()
        {
            return movies;
        }
}
        class Adminmanagement extends BaseAdmin implements AdminOperations {
            private MovieCollection movieCollection;
            private List<Reviews> reviews;

            public Adminmanagement(String adminID, String adminPassword, MovieCollection movieCollection, List<Reviews> reviews) {
                super(adminID, adminPassword);
                this.movieCollection = movieCollection;
                this.reviews = reviews;
            }

            public void dashboard() {
                System.out.println("Admin " + adminID + " dashboard accessed. Total admins :" + getAdminCount());
            }

            @Override
            public void manageMovies() {
                System.out.println(adminID);
            }





            public void addMovie(movie Movie)
            {
                movie.setTitle(Movie);
            }
            public List<Movies> getMovies()
            {
                return movies;
            }
        }

        class Adminmanagement extends BaseAdmin implements AdminOperations {
            private MovieCollection movieCollection;
            private List<Reviews> reviews;

            public Adminmanagement(String adminID, String adminPassword, MovieCollection movieCollection, List<Reviews> reviews) {
                super(adminID, adminPassword);
                this.movieCollection = movieCollection;
                this.reviews = reviews;
            }

            public void dashboard() {
                System.out.println("Admin " + adminID + " dashboard accessed. Total admins :" + A.getAdminCount());
            }

            @Override
            public void manageMovies() {
                System.out.println(adminID);
            }

            public void manageMovieForm() {
                System.out.println(adminID);
            }

            public void showAddMOvieForm() {
                System.out.println(adminID);
            }

            public void updateMovie() {
                System.out.println(adminID);
            }

            public void deleteMovie() {
                System.out.println(adminID);
            }

            @Override
            public void promoteUser() {
                System.out.println(adminID);
            }

            @Override
            public void deleteUser() {
                System.out.println(adminID);
            }

            public void manageReviews() {
                System.out.println(adminID);
            }

            public void deleteReview() {
                System.out.println(adminID);
            }
        }

        public class Main
        {
            public static void main(String[] args)
            {
                Adminmanagement admin = new Adminmanagement(ADM001);
                admin.manageMovieForm();
                admin.showAddMOvieForm();
                admin.updateMovie();
                admin.deleteMovie();
                admin.manageReviews();
                admin.promoteUser();
                admin.dashboard();
            }
        }
