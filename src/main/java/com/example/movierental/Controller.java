package com.example.movierental;

import com.example.movierental.model.User;
import com.example.movierental.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UserServices userServices;

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
    public String registration(Model model, @RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword) {
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
    public String login(Model model, @RequestParam String name, @RequestParam String password){
        if(userServices.LoginUser(name, password))
            return "profile";
        else {
            model.addAttribute("message3", "Invalid username or password");
            return "login";
        }
    }

}
