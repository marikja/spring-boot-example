package com.example.userms.common.integrator.car.model.response;

import com.example.userms.common.integrator.car.constant.CarType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RentCarResponse(
        UUID userId,
        UUID carId,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        String vin,
        String make,
        String model,
        CarType type,
        BigDecimal price
) {
}
