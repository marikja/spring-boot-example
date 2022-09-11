package com.example.userms.module.user.exception;

import com.example.userms.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends ApiException {

    public UserAlreadyExistsException() {
        super("User already exists");
    }
}