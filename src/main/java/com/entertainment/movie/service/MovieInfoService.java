package com.entertainment.movie.service;

import com.entertainment.movie.dto.MovieDTO;
import com.entertainment.movie.dto.UserRatingDTO;
import com.entertainment.movie.model.Movie;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MovieInfoService {

    List<MovieDTO> getAllMovies(PageRequest pageRequest);

    void updateMovieRating(UserRatingDTO userRatingDTO);

    MovieDTO getMovie(String movieName);

    void saveMovie(MovieDTO movieDTO);
}
