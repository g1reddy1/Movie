package com.entertainment.movie.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="movies")
@Getter
@Setter
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="movie_name")
    private String movieName;

    @Column(name="average_rating")
    private Double averageRating;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<UserRating> userRatings;
}
