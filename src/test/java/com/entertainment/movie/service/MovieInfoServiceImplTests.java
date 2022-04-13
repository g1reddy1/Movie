package com.entertainment.movie.service;


import com.entertainment.movie.dto.MovieDTO;
import com.entertainment.movie.model.Movie;
import com.entertainment.movie.repository.MovieRepository;
import com.entertainment.movie.repository.UserRatingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MovieInfoServiceImplTests {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRatingRepository userRatingRepository;

    @InjectMocks
    private MovieInfoServiceImpl movieInfoService;

    @Test
    public void testGetAllMovies() {
        Movie movie = new Movie();
        movie.setMovieName("RRR");
        movie.setAverageRating(5.0);
        List<Movie> movieList = new ArrayList<Movie>();
        movieList.add(movie);
        Page<Movie> page = new PageImpl<>(movieList);
        PageRequest pageRequest = PageRequest.of(0,10);
        Mockito.when(movieRepository.findAll(pageRequest)).thenReturn(page);
        List<MovieDTO> response = movieInfoService.getAllMovies(pageRequest);
        assertEquals(response.get(0).getMovieName(), "RRR");
    }

    @Test
    public void testGetMovie() {
        Movie movie = new Movie();
        movie.setMovieName("RRR");
        movie.setAverageRating(5.0);
        Mockito.when(movieRepository.findByMovieName("RRR")).thenReturn(Optional.of(movie));
        MovieDTO response = movieInfoService.getMovie("RRR");
        assertEquals(response.getMovieName(), "RRR");
    }
}
