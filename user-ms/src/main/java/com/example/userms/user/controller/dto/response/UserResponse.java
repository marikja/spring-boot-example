package com.example.userms.user.controller.dto.response;

import com.example.userms.common.integrator.car.model.response.RentCarResponse;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserResponse (
    UUID userId,
    String firstName,
    String lastName,
    String email,
    @Nullable LocalDate birth,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<RentCarResponse> rentCars
) {
}
