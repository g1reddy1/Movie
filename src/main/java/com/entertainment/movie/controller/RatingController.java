package com.entertainment.movie.controller;

import com.entertainment.movie.dto.UserRatingDTO;
import com.entertainment.movie.model.Movie;
import com.entertainment.movie.model.UserRating;
import com.entertainment.movie.service.MovieInfoService;
import com.entertainment.movie.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping(value="/rate")
    public void saveUserRating(@RequestBody UserRatingDTO userRatingDTO) {
        ratingService.setRatingForMovie(userRatingDTO);
    }

}
