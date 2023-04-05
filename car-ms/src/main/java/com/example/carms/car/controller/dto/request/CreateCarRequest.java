package com.example.carms.car.controller.dto.request;

import com.example.carms.car.constant.CarType;

import java.math.BigDecimal;

public record CreateCarRequest (
    String vin,
    String make,
    String model,
    Integer horsePower,
    CarType type,
    BigDecimal price
){}
