package com.example.adminbackend.controller;

import com.example.movierental.model.User;
import com.example.movierental.model.Movie;
import com.example.movierental.services.UserServices;
import com.example.movierental.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello World");
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/registration")
    //@ResponseBody
    public String registration(Model model, @RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword)
    {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            model.addAttribute("message1", "Please fill in all fields");
            return "registration";
        }
        // username error
        if (userServices.isUsernameTaken(name)
        {
            model.addAttribute("message1", "Username already taken");
            return "registration";
        }
        //email error
        if (userServices.isemailTaken(email))
        {
            model.addAttribute("message1", "Email already taken");
            return "registration";
        }
        if (!password.equals(repeatPassword)) {
            model.addAttribute("message2", "Passwords do not match");
            return "registration";
        }
        User u1 = new User(name, email, password);
        if(userServices.Adduser(u1))
            return "login";
        else {
            model.addAttribute("message1", "User already exists");
            return "registration";
        }
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String name, @RequestParam String password) {
        if (userServices.LoginUser(name, password)) {
            System.out.println("Loaded login after");
            model.addAttribute("movies", movieService.getAllMovies("title"));
            return "movieList";  // ✅ matches movieList.html
        } else {
            model.addAttribute("message3", "Invalid username or password");
            return "login";
        }
    }

}
