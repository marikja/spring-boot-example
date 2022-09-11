package com.example.carms.module.car.service.leasing;

import com.example.carms.module.car.constant.CarType;

import java.math.BigDecimal;

public interface LeasingCalculator {

    CarType getCarType();

    BigDecimal calculateLeasing(BigDecimal coefficient, BigDecimal carPrice, int monthCount);
}
