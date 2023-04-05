package com.example.carms.car.controller.dto.response;

import com.example.carms.car.constant.CarType;

import java.math.BigDecimal;
import java.util.UUID;

public record CarResponse(
        UUID carId,
        String vin,
        String make,
        String model,
        Integer horsePower,
        CarType type,
        BigDecimal price
) {
}
