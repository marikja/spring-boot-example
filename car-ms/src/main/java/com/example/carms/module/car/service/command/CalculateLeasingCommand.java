package com.example.carms.module.car.service.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public record CalculateLeasingCommand(

    @NotNull
    UUID carId,

    @Positive
    int monthCount
) {
}
