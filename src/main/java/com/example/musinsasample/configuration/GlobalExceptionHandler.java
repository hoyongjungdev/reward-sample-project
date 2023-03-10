package com.example.musinsasample.configuration;

import com.example.musinsasample.exception.BaseException;
import com.example.musinsasample.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BaseException.class})
    protected ResponseEntity<ErrorResponse> handleException(BaseException exception) {
        log.error("Exception : {}", exception.getErrorCode());
        return ErrorResponse.toResponseEntity(exception);
    }
}
