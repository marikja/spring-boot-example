package com.example.carms.car.exception;

import com.example.carms.common.exception.DefaultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends DefaultException {

    public CarNotFoundException() {
        super("Car not found");
    }
}
