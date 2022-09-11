package com.example.carms.module.car.exception;

import com.example.carms.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarAlreadyExistsException extends ApiException {

    public CarAlreadyExistsException() {
        super("Car already exists");
    }
}
