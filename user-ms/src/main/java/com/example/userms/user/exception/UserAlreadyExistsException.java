package com.example.userms.user.exception;

import com.example.userms.common.exception.DefaultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends DefaultException {

    public UserAlreadyExistsException() {
        super("User already exists");
    }
}