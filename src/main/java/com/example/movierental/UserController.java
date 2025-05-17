package com.example.movierental;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/movies/catalog";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        if (user.getName() == null || user.getPassword() == null) {
            model.addAttribute("error", "Username and password are required");
            return "login";
        }

        User authenticatedUser = userServices.loginUser(user.getName(), user.getPassword());
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return "redirect:/movies/catalog";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (user.getName() == null || user.getName().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            model.addAttribute("error", "Username and password are required");
            return "registration";
        }

        if (userServices.registerUser(user)) {
            return "redirect:/login";
        }
        model.addAttribute("error", "Username already exists");
        return "registration";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }
}