package com.example.carms.module.car.service.command;

import com.example.carms.module.car.constant.CarType;

import java.math.BigDecimal;

public record CreateCarCommand(
        String vin,
        String make,
        String model,
        Integer horsePower,
        CarType type,
        BigDecimal price
) {
}
