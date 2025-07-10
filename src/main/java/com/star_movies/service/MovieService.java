package com.star_movies.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.star_movies.model.Movie;
import com.star_movies.repository.MovieRepository;

@Service

public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        Optional<Movie> movieOps = movieRepository.findById(id);
        Movie movie = movieOps.orElse(null);
        return movie;
    }

    public Movie save(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return savedMovie;
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

}
