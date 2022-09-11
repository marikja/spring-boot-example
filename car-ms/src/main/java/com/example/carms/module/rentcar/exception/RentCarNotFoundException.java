package com.example.carms.module.rentcar.exception;

import com.example.carms.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentCarNotFoundException extends ApiException {

    public RentCarNotFoundException() {
        super("RentCar not found");
    }
}
