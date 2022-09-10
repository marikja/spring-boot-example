package com.example.userms.common.integrator.car.model.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record RentCarRequest(
        UUID userId,
        UUID carId,
        LocalDateTime fromDate,
        LocalDateTime toDate
) {
}
