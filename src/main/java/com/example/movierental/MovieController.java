package com.example.movierental;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final UserServices userServices;



    @Autowired
    public MovieController(MovieService movieService, UserServices userServices) {
        this.movieService = movieService;
        this.userServices = userServices;
    }

    @GetMapping("/catalog")
    public String showMovieCatalog(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            User currentUser = userServices.getUserByName(user.getName());
            if (currentUser == null) {
                session.invalidate();
                redirectAttributes.addFlashAttribute("error", "Session expired");
                return "redirect:/login";
            }
            session.setAttribute("user", currentUser);

            List<Movie> topMovies = movieService.getTopMovies(5);
            List<Movie> recentlyWatched = movieService.getRecentlyWatched();

            model.addAttribute("topMovies", topMovies);
            model.addAttribute("recentlyWatched", recentlyWatched);
            model.addAttribute("watchlist", currentUser.getWatchlist());
            model.addAttribute("movieService", movieService);

            return "movieCatalog";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading catalog: " + e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/all")
    public String listAllMovies(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            List<Movie> allMovies = movieService.getAllMovies("title");
            model.addAttribute("movies", allMovies);
            model.addAttribute("watchlist", user.getWatchlist());
            return "movieList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading movies: " + e.getMessage());
            return "redirect:/movies/catalog";
        }
    }


    @GetMapping("/view/{id}")
    public String viewMovie(@PathVariable Long id, Model model, HttpSession session) {
        // Check authentication
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Get movie
        Optional<Movie> movie = movieService.getMovieById(id);
        if (!movie.isPresent()) {
            return "redirect:/movies/all";
        }

        // Add to model
        model.addAttribute("movie", movie.get());
        model.addAttribute("inWatchlist", user.getWatchlist().contains(id));
        model.addAttribute("similarMovies", movieService.getSimilarMovies(movie.get().getGenre(), id));

        return "movieView";
    }

    @PostMapping("/watch/{id}")
    public String Watchlist(@PathVariable Long id,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes,
                                  @RequestHeader(required = false) String referer) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            User currentUser = userServices.getUserByName(user.getName());
            if (currentUser.getWatchlist().contains(id)) {
                currentUser.removeFromWatchlist(id);
                redirectAttributes.addFlashAttribute("success", "Removed from watchlist");
            } else {
                currentUser.addToWatchlist(id);
                redirectAttributes.addFlashAttribute("success", "Added to watchlist");
            }
            userServices.updateUserWatchlist(currentUser.getName(), new ArrayList<>(currentUser.getWatchlist()));
            session.setAttribute("user", currentUser);

            // Redirect back to the previous page
            String redirectUrl = (referer != null && !referer.isEmpty()) ? referer : "/movies/catalog";
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating watchlist: " + e.getMessage());
            return "redirect:/movies/catalog";
        }
    }

    @PostMapping("/watch/remove/{id}")
    public String removeFromWatchlist(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            User currentUser = userServices.getUserByName(user.getName());
            currentUser.removeFromWatchlist(id);
            userServices.updateUserWatchlist(currentUser.getName(), new ArrayList<>(currentUser.getWatchlist()));
            session.setAttribute("user", currentUser);

            redirectAttributes.addFlashAttribute("success", "Removed from watchlist");
            return "redirect:/movies/catalog";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error removing from watchlist: " + e.getMessage());
            return "redirect:/movies/catalog";
        }
    }
    @GetMapping("/manage")
    public String manageMovies(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            List<Movie> movies = movieService.getAllMovies("title");
            model.addAttribute("movies", movies);
            return "movieManagement";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading management page: " + e.getMessage());
            return "redirect:/movies/catalog";
        }
    }

    @GetMapping("/add")
    public String showAddMovieForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }
        model.addAttribute("movie", new Movie());
        return "movieForm";
    }

    @PostMapping("/add")
    public String addNewMovie(
            @ModelAttribute Movie movie,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            if (movie.getPosterUrl() == null || movie.getPosterUrl().isEmpty()) {
                movie.setPosterUrl("https://via.placeholder.com/300x450?text=No+Poster");
            }

            movieService.saveMovie(movie);
            redirectAttributes.addFlashAttribute("success", "Movie added successfully");
            return "redirect:/movies/manage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding movie: " + e.getMessage());
            return "redirect:/movies/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditMovieForm(
            @PathVariable Long id,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            Movie movie = movieService.getMovieById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
            model.addAttribute("movie", movie);
            return "movieEdit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading edit form: " + e.getMessage());
            return "redirect:/movies/manage";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateMovie(
            @PathVariable Long id,
            @ModelAttribute Movie movie,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            movie.setId(id);
            if (movie.getPosterUrl() == null || movie.getPosterUrl().isEmpty()) {
                Movie existing = movieService.getMovieById(id).orElseThrow();
                movie.setPosterUrl(existing.getPosterUrl());
            }

            movieService.saveMovie(movie);
            redirectAttributes.addFlashAttribute("success", "Movie updated successfully");
            return "redirect:/movies/manage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating movie: " + e.getMessage());
            return "redirect:/movies/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteMovieById(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            // Remove  watchlists
            List<User> allUsers = userServices.readUsers();
            allUsers.forEach(u -> {
                if (u.getWatchlist().contains(id)) {
                    u.removeFromWatchlist(id);
                    userServices.updateUserWatchlist(u.getName(), new ArrayList<>(u.getWatchlist()));
                }
            });

            movieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute("success", "Movie deleted successfully");
            return "redirect:/movies/manage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting movie: " + e.getMessage());
            return "redirect:/movies/manage";
        }
    }

    @GetMapping("/search")
    public String searchMovies(
            @RequestParam String query,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            List<Movie> searchResults = movieService.searchMovies(query);
            model.addAttribute("movies", searchResults);
            model.addAttribute("searchQuery", query);
            model.addAttribute("watchlist", user.getWatchlist());
            model.addAttribute("movieService", movieService);

            return "movieSearch";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Search error: " + e.getMessage());
            return "redirect:/movies/catalog";
        }
    }

    @PostMapping("/rent/{id}")
    public String rentMovie(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/login";
            }

            Movie movie = movieService.getMovieById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found"));



            redirectAttributes.addFlashAttribute("success", "Movie rented successfully: " + movie.getTitle());
            return "redirect:/movies/view/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error renting movie: " + e.getMessage());
            return "redirect:/movies/view/" + id;
        }
    }
}