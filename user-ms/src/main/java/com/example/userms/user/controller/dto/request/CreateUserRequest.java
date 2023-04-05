package com.example.userms.user.controller.dto.request;

import org.springframework.lang.Nullable;

import java.time.LocalDate;


public record CreateUserRequest(
    String firstName,
    String lastName,
    String email,
    String password,
    @Nullable LocalDate birth
){
}
