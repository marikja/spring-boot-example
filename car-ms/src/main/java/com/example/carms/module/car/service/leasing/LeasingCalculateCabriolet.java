package com.example.carms.module.car.service.leasing;

import com.example.carms.module.car.constant.CarType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LeasingCalculateCabriolet implements LeasingCalculator {

    @Override
    public CarType getCarType() {
        return CarType.CABRIOLET;
    }

    @Override
    public BigDecimal calculateLeasing(BigDecimal coefficient, BigDecimal carPrice, int monthCount) {
        /*
          Some complicated algorithm for calculating leasing for CABRIOLET type
          .
          .
          .
         */

        return carPrice.multiply(coefficient.add(BigDecimal.ONE)).divide(BigDecimal.valueOf(monthCount));
    }
}
