package com.example.carms.car.controller.dto.response;

import com.example.carms.car.constant.CarType;

import java.math.BigDecimal;

public record CarResponse(
        String vin,
        String make,
        String model,
        Integer horsePower,
        CarType type,
        BigDecimal price
) {
}
