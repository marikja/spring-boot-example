package com.example.userms.module.user.controller.dto.request;

import org.springframework.lang.Nullable;


public record CreateUserRequest(
    String firstName,
    String lastName,
    String email,
    String password,
    @Nullable Integer age
){
}
