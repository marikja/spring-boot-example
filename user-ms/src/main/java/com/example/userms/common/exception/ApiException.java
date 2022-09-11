package com.example.userms.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ApiException extends RuntimeException {

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
