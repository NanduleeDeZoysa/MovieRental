package com.example.adminbackend.controller;

import org.springframework.ui.Model;
import com.example.adminbackend.model.Movie;
import com.example.adminbackend.model.User;
import com.example.adminbackend.services.AdminService;
import com.example.adminbackend.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserServices userServices;

    // Dashboard
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("movieCount",adminService.getMovieCount());
        model.addAttribute("UserCount",adminService.getUserCount());
        model.addAttribute("reviewCount",adminService.getReviewCount());
        model.addAttribute("totalCount",adminService.getTotalCount());
        return "dashboard";
    }
    // movies

    @GetMapping("/movies")
    public String manageMovies(Model model) {
        model.addAttribute("movies",adminService.getMovies());
        return "Movies";
    }
    @GetMapping("/movies/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie",new Movie());
        return "add-movie";
    }
    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute Movie movie) {
        adminService.addMovie(movie);
        return "redirect:/admin/movies";
    }
    @GetMapping("/movies/update/{id}")
    public String showUpdateMovieForm(@PathVariable("id") int movieId, Model model) {
        Optional<Movie> movie = adminService.getMovieById(movieId);
        model.addAttribute("Movie",movie.orElse(new Movie()));
        return "Update - movie";
    }
    @PostMapping("/movies/update")
    public String updateMovie(@ModelAttribute Movie movie)
    {
        adminService.updateMovie(movie);
        return "redirect:/admin/movies";
    }
    @GetMapping("/movie/delete/{id}")
    public String deleteMovie(@PathVariable("id") int movieId)
    {
        adminService.deleteMovie(movieId);
        return "redirect:/admin/movies";
    }

    // user
    @GetMapping("/users")
    public String manageUsers(Model model)
    {
        model.addAttribute("Users", adminService.getUsers());
        return "Users";
    }
    // promote user to admin
    @GetMapping("/users/promote/{id}")
    public String promoteToAdmin(@PathVariable("id") int id)
    {
      Optional<User> user = userServices.findById(id);
      if (user.isPresent()) {
          user.get().setUserRole("ADMIN");
          userServices.saveUser(userServices.readUsers());
          //userServices.saveUser(user.get());
      }
      else {
          System.out.println("User not found");
      }
      return "redirect:/admin/users";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id)
    {
        adminService.deleteUser(id);
        return "redirect:/admin/users";
    }

    // reviews

    @GetMapping("/reviews")
    public String manageReviews(Model model)
    {
        model.addAttribute("reviews", adminService.getReview());
        return "Reviews";
    }
    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable("id")int id)
    {
        adminService.deleteReviews(id);
        return "redirect:/admin/reviews";
    }
}




















