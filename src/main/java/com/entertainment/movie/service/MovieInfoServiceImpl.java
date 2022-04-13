package com.entertainment.movie.service;

import com.entertainment.movie.dto.MovieDTO;
import com.entertainment.movie.dto.UserRatingDTO;
import com.entertainment.movie.exception.MovieAlreadyExistsException;
import com.entertainment.movie.model.Movie;
import com.entertainment.movie.repository.MovieRepository;
import com.entertainment.movie.repository.UserRatingRepository;
import com.entertainment.movie.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Override
    public List<MovieDTO> getAllMovies(PageRequest pageRequest) {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findAll(pageRequest).toList();
        movieList.stream().map(movie -> movieDTOList.add(new MovieDTO(movie.getMovieName(), movie.getAverageRating()))).collect(Collectors.toList());
        return movieDTOList;
    }

    @Override
    public void updateMovieRating(UserRatingDTO userRatingDTO) {
        Movie movie = movieRepository.findByMovieName(userRatingDTO.getMovieName()).orElseThrow(() -> new ResourceNotFoundException("Movie not avialable with movie name: "+ userRatingDTO.getMovieName()));
        Double rating = movie.getAverageRating();
        int numberOfRatings = movie.getUserRatings().size();
        rating = (rating * numberOfRatings + userRatingDTO.getUserRating()) / (numberOfRatings + 1);
        movie.setAverageRating(rating);
        movieRepository.save(movie);
    }

    @Override
    public MovieDTO getMovie(String movieName) {
        Movie movie = movieRepository.findByMovieName(movieName).orElseThrow(() -> new ResourceNotFoundException("NA"));
        return new MovieDTO(movie.getMovieName(), movie.getAverageRating());
    }

    @Override
    public void saveMovie(MovieDTO movieDTO) {
        Optional<Movie> movieExisting = movieRepository.findByMovieName(movieDTO.getMovieName());
        if(movieExisting.isPresent()) {
            throw new MovieAlreadyExistsException("Movie already exists");
        }
        Movie movie = new Movie();
        movie.setMovieName(movieDTO.getMovieName());
        if(movieDTO.getAverageRating() == null) {
            movieDTO.setAverageRating(0.0);
        }
        movie.setAverageRating(movieDTO.getAverageRating());
        movieRepository.save(movie);
    }
}
