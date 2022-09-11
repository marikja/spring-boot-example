package com.example.carms.module.car.exception;

import com.example.carms.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends ApiException {

    public CarNotFoundException() {
        super("Car not found");
    }
}
