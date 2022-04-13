package com.entertainment.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerWithResponse extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException re) {
        return new ResponseEntity<>(re.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UsernameNotFoundException ue) {
        return new ResponseEntity<>(ue.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<Object> resourceNotFound(MovieAlreadyExistsException me) {
        return new ResponseEntity<>(me.getMessage(), HttpStatus.OK);
    }
}
