package org.example.reposlistingspring.exceptions.mappers;

import org.example.reposlistingspring.exceptions.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {HttpException.class})
    public ResponseEntity<ErrorMessage> handleHttpException(HttpException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
    }
}
