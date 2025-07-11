package com.star_movies.controller.client;

import com.star_movies.model.Movie;
import com.star_movies.service.MovieService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomePageController {

    private final MovieService movieService;

    public HomePageController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("listMovie", movies);
        return "client/home";
    }

    @GetMapping("/movie/{id}")
    public String watchMoviePage(Model model, @PathVariable Long id) {
        Movie movieToWatch = movieService.findById(id);
        if (movieToWatch == null) {
            return "redirect:/";
        }
        model.addAttribute("movieToWatch", movieToWatch);
        return "client/watching-page";
    }
}