package com.example.carms.module.rentcar.service.command;

import com.sun.istack.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateRentCarCommand(

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
