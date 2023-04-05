package com.example.carms.car.service.leasing;

import com.example.carms.car.constant.CarType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LeasingCalculateSedan implements LeasingCalculator {

    @Override
    public CarType getCarType() {
        return CarType.SEDAN;
    }

    @Override
    public BigDecimal calculateLeasing(BigDecimal coefficient, BigDecimal carPrice, int monthCount) {
        /*
          Some complicated algorithm for calculating leasing for SEDAN type
          .
          .
          .
         */

        return carPrice.multiply(coefficient.add(BigDecimal.ONE)).divide(BigDecimal.valueOf(monthCount));
    }
}
