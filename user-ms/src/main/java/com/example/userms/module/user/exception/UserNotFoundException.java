package com.example.userms.module.user.exception;

import com.example.userms.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        super("User not found");
    }
}
