package com.example.carms.car.service.action;

import com.example.carms.car.constant.CarType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateCarAction(

        @NotBlank
        String vin,

        @NotBlank
        String make,

        @NotBlank
        String model,

        @NotNull
        @Positive
        Integer horsePower,

        @NotNull
        CarType type,

        @NotNull
        @Positive
        BigDecimal price

) {
}
