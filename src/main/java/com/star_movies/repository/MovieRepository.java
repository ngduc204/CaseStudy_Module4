package com.star_movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.star_movies.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
