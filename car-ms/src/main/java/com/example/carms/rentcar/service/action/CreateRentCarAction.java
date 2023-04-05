package com.example.carms.rentcar.service.action;

import com.sun.istack.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateRentCarAction(

        @NotNull
        UUID carId,

        @NotNull
        UUID userId,

        @NotNull
        LocalDateTime fromDate,

        @NotNull
        LocalDateTime toDate
) {
}
