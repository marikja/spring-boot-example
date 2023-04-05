package com.example.userms.common.config;

import com.example.userms.common.dto.response.ErrorResponse;
import com.example.userms.common.exception.DefaultException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(DefaultException.class)
    public final ResponseEntity<ErrorResponse> handleException(DefaultException e) {
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        final HttpStatus httpStatus = responseStatus != null ? responseStatus.code() : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), new HttpHeaders(), httpStatus);
    }

}
