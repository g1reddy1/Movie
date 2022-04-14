package com.entertainment.movie.repository;

import com.entertainment.movie.model.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    @Query(nativeQuery = true, value = "select * from user_rating where username=:username and movie_name=:movieName")
    public Optional<UserRating> findByUsernameAndMovieName(String username, String movieName);
}
