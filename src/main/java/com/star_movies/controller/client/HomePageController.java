package com.star_movies.controller.client;
import com.star_movies.model.Movie;
import com.star_movies.model.User;
import com.star_movies.service.MovieService;
import com.star_movies.service.UserService;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class HomePageController {
    private final MovieService movieService;
    private final UserService userService;
    public HomePageController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }
    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser != null) {
            String email = currentUser.getUsername();
            User user = userService.findByEmail(email);
            if (user != null) {
                model.addAttribute("loggedInUsername", user.getFullName());
                model.addAttribute("loggedInUserAvatar", user.getAvatar());
                model.addAttribute("loggedInUserRoleId", user.getRole().getId());
            }
        }
        List<Movie> movies = movieService.findAll();
        model.addAttribute("listMovie", movies);
        return "client/home";
    }
    @GetMapping("/movie/{id}")
    public String watchMoviePage(Model model, @PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser != null) {
            String email = currentUser.getUsername();
            User user = userService.findByEmail(email);
            if (user != null) {
                model.addAttribute("loggedInUsername", user.getFullName());
                model.addAttribute("loggedInUserAvatar", user.getAvatar());
                model.addAttribute("loggedInUserRoleId", user.getRole().getId());
            }
        }
        Movie movieToWatch = movieService.findById(id);
        if (movieToWatch == null) {
            return "redirect:/";
        }
        model.addAttribute("movieToWatch", movieToWatch);
        return "client/watching-page";
    }
}