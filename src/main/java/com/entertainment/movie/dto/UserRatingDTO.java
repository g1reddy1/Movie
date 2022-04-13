package com.entertainment.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRatingDTO {

    private String userName;

    private String movieName;

    private Double userRating;
}
