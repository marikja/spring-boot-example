package com.example.carms.module.car.service.model;

import java.math.BigDecimal;
import java.util.UUID;

public record LeasingModel(
        UUID carId,
        int monthCount,
        BigDecimal price,
        BigDecimal monthlyPayment
) {
}
