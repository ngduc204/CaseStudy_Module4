package com.star_movies.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import com.star_movies.model.Movie;
import com.star_movies.service.MovieService;

@Controller
@RequestMapping("/admin/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "admin/movie/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin/movie/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.findById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "admin/movie/form";
        } else {
            return "redirect:/admin/movies";
        }
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute("movie") Movie movie) {

        movieService.save(movie);
        return "redirect:/admin/movies";
    }

    @GetMapping("/detail/{id}")
    public String viewMovieDetail(@PathVariable Long id, Model model) {
        Movie movie = movieService.findById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "admin/movie/detail";
        } else {
            return "redirect:/admin/movies";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return "redirect:/admin/movies";
    }
}
