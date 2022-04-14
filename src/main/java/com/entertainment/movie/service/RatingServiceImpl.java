package com.entertainment.movie.service;

import com.entertainment.movie.dto.UserRatingDTO;
import com.entertainment.movie.model.Movie;
import com.entertainment.movie.model.UserRating;
import com.entertainment.movie.repository.MovieRepository;
import com.entertainment.movie.repository.UserRatingRepository;
import com.entertainment.movie.repository.UserRepository;
import com.entertainment.movie.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void setRatingForMovie(UserRatingDTO userRatingDTO) {
        Movie movie = movieRepository.findByMovieName(userRatingDTO.getMovieName()).orElseThrow(() -> new ResourceNotFoundException("Movie Not Found with movie name: " + userRatingDTO.getMovieName()));
        movieInfoService.updateMovieRating(userRatingDTO);
        UserRating userRating = new UserRating();
        userRating.setUser(userRepository.findByUsername(userRatingDTO.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userRatingDTO.getUserName())));
        userRating.setMovie(movie);
        if(userRatingDTO.getUserRating() == null)
            userRatingDTO.setUserRating(0.0);
        userRating.setRating(userRatingDTO.getUserRating());
        Optional<UserRating> existingUserRating = userRatingRepository.findByUsernameAndMovieName(userRatingDTO.getUserName(), userRatingDTO.getMovieName());
        if(existingUserRating.isPresent())
            userRating.setId(existingUserRating.get().getId());
        userRatingRepository.save(userRating);

    }
}
