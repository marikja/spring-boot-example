package com.example.carms.module.car.service.action;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public record CalculateLeasingAction(

    @NotNull
    UUID carId,

    @Positive
    int monthCount
) {
}
