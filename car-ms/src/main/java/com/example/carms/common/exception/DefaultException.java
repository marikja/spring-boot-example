package com.example.carms.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DefaultException extends RuntimeException {

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
