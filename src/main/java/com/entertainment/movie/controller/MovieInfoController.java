package com.entertainment.movie.controller;

import com.entertainment.movie.dto.MovieDTO;
import com.entertainment.movie.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    @GetMapping(value = "/getAllMovies/{pagenumber}")
    public ResponseEntity<List<MovieDTO>> getAllMovies(@PathVariable("pagenumber") int pagenumber) {
        PageRequest pageRequest = PageRequest.of(pagenumber, 10);
        List<MovieDTO> movies = movieInfoService.getAllMovies(pageRequest);
        return new ResponseEntity<List<MovieDTO>>(movies, HttpStatus.OK);

    }

    @GetMapping(value = "/getrating/{movie}")
    public MovieDTO getMovieRating(@PathVariable("movie") String movie) {
        return movieInfoService.getMovie(movie);
    }

    @PostMapping(value="/addmovie")
    public void addMovie(@RequestBody MovieDTO movieDTO) {
        movieInfoService.saveMovie(movieDTO);
    }

    @PostMapping(value="/addMovies")
    public void addMovies(@RequestBody MovieDTO movieDTO) {
        movieInfoService.saveMovies();
    }
}
