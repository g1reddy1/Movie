package com.entertainment.movie.exception;

import com.entertainment.movie.model.Movie;

public class MovieAlreadyExistsException extends RuntimeException{

    public MovieAlreadyExistsException(String msg) {
        super(msg);
    }
}
