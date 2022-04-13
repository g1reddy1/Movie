package com.entertainment.movie.repository;

import com.entertainment.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    Boolean existsByMovieName(String movieName);

    Optional<Movie> findByMovieName(String movieName);
}
