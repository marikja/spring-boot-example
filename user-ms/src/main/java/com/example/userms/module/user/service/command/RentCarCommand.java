package com.example.userms.module.user.service.command;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record RentCarCommand(

        @NotNull
        UUID userId,

        @NotNull
        UUID carId,

        @NotNull
        LocalDateTime fromDate,

        @NotNull
        LocalDateTime toDate

) {
}
