package com.entertainment.movie.controller;

import com.entertainment.movie.dto.MovieDTO;
import com.entertainment.movie.service.MovieInfoService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MovieInfoControllerTests {

    @InjectMocks
    private MovieInfoController movieInfoController;

    @Mock
    private MovieInfoService movieInfoService;


    @Test
    public void testGetAllMovies() throws Exception {
        MovieDTO movieDTO = new MovieDTO("RRR", 5.0);
        List<MovieDTO> movieDTOList = new ArrayList<MovieDTO>();
        movieDTOList.add(movieDTO);
        PageRequest pageRequest = PageRequest.of(0,10);
        Mockito.when(movieInfoService.getAllMovies(pageRequest)).thenReturn(movieDTOList);
        ResponseEntity<List<MovieDTO>> response = movieInfoController.getAllMovies(0);
        assertEquals(response.getBody().get(0).getMovieName(), "RRR");
    }

    @Test
    public void testGetMovieRating() throws Exception {
        MovieDTO movieDTO = new MovieDTO("RRR", 5.0);
        Mockito.when(movieInfoService.getMovie("RRR")).thenReturn(movieDTO);
        MovieDTO response = movieInfoController.getMovieRating("RRR");
        assertEquals(response.getAverageRating(), 5.0);
    }
}
