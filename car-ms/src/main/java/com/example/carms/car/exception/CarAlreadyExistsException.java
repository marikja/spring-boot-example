package com.example.carms.car.exception;

import com.example.carms.common.exception.DefaultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarAlreadyExistsException extends DefaultException {

    public CarAlreadyExistsException() {
        super("Car already exists");
    }
}
