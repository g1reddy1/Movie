package com.entertainment.movie.service;

import com.entertainment.movie.dto.UserRatingDTO;
import com.entertainment.movie.model.UserRating;

public interface RatingService {

    void setRatingForMovie(UserRatingDTO userRatingDTO);
}
