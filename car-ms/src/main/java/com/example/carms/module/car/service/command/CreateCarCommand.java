package com.example.carms.module.car.service.command;

import com.example.carms.module.car.constant.CarType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateCarCommand(

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
