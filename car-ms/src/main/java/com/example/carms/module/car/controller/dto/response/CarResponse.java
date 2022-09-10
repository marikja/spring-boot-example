package com.example.carms.module.car.controller.dto.response;

import com.example.carms.module.car.constant.CarType;

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
