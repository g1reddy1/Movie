package com.entertainment.movie.config;

import com.entertainment.movie.repository.MovieRepository;
import com.entertainment.movie.repository.UserRatingRepository;
import com.entertainment.movie.repository.UserRepository;
import com.entertainment.movie.service.MovieInfoService;
import com.entertainment.movie.service.MovieInfoServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MovieApplicationTestConfiguration {


    @Bean
    @Primary
    public MovieInfoService getMovieInfoService() {
        return Mockito.mock(MovieInfoServiceImpl.class);
    }

    @Bean
    @Primary
    public MovieRepository getMovieRepo() {
        return Mockito.mock(MovieRepository.class);
    }

    @Bean
    @Primary
    public UserRatingRepository getUserRatingRepo() {
        return Mockito.mock(UserRatingRepository.class);
    }

    @Bean
    @Primary
    public UserRepository getUserRepo() {
        return Mockito.mock(UserRepository.class);
    }
}
