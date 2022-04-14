package com.entertainment.movie.service;

import com.entertainment.movie.dto.MovieDTO;
import com.entertainment.movie.dto.UserRatingDTO;
import com.entertainment.movie.exception.MovieAlreadyExistsException;
import com.entertainment.movie.model.Movie;
import com.entertainment.movie.model.UserRating;
import com.entertainment.movie.repository.MovieRepository;
import com.entertainment.movie.repository.UserRatingRepository;
import com.entertainment.movie.exception.ResourceNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Override
    public List<MovieDTO> getAllMovies(PageRequest pageRequest) {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findAll(pageRequest).toList();
        movieList.stream().map(movie -> movieDTOList.add(new MovieDTO(movie.getMovieName(), movie.getAverageRating()))).collect(Collectors.toList());
        return movieDTOList;
    }

    @Override
    public void updateMovieRating(UserRatingDTO userRatingDTO) {
        Movie movie = movieRepository.findByMovieName(userRatingDTO.getMovieName()).orElseThrow(() -> new ResourceNotFoundException("Movie not avialable with movie name: " + userRatingDTO.getMovieName()));
        Double rating = movie.getAverageRating();
        int numberOfRatings = movie.getUserRatings().size();
        int latestNumberOfRatings = numberOfRatings;
        double existingUserRating = 0;
        Optional<UserRating> existingUserRatingEntity = userRatingRepository.findByUsernameAndMovieName(userRatingDTO.getUserName(), userRatingDTO.getMovieName());
        if (existingUserRatingEntity.isPresent()) {
            existingUserRating = existingUserRatingEntity.get().getRating();
        }
        else {
            latestNumberOfRatings++;
        }
        rating = (rating * (numberOfRatings) + userRatingDTO.getUserRating()-existingUserRating) / (latestNumberOfRatings);
        movie.setAverageRating(rating);
        movieRepository.save(movie);
    }

    @Override
    public MovieDTO getMovie(String movieName) {
        Movie movie = movieRepository.findByMovieName(movieName).orElseThrow(() -> new ResourceNotFoundException("NA"));
        return new MovieDTO(movie.getMovieName(), movie.getAverageRating());
    }

    @Override
    public void saveMovie(MovieDTO movieDTO) {
        Optional<Movie> movieExisting = movieRepository.findByMovieName(movieDTO.getMovieName());
        if (movieExisting.isPresent()) {
            throw new MovieAlreadyExistsException("Movie already exists");
        }
        Movie movie = new Movie();
        movie.setMovieName(movieDTO.getMovieName());
        if (movieDTO.getAverageRating() == null) {
            movieDTO.setAverageRating(0.0);
        }
        movie.setAverageRating(movieDTO.getAverageRating());
        movieRepository.save(movie);
    }

    @Override
    public void saveMovies() {
        for (int id = 1; id < 100; id++) {
            String urls = "https://api.themoviedb.org/3/movie/" + id + "?api_key=f1bb06f3ec825f1afd57391e3b107b8b";
            try {
                URL url = new URL(urls);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = bufferedReader.lines().collect(Collectors.joining());
                System.out.println(line);
                JSONObject jsonObject = new JSONObject(line);
                String movieName = jsonObject.getString("original_title");
                Double averageRating = jsonObject.getDouble("vote_average");
                saveMovie(new MovieDTO(movieName, averageRating));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
