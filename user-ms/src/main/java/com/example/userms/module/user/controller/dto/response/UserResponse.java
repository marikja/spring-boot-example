package com.example.userms.module.user.controller.dto.response;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponse (
    UUID userId,
    String firstName,
    String lastName,
    String email,
    @Nullable Integer age,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
