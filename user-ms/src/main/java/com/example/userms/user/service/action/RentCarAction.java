package com.example.userms.user.service.action;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record RentCarAction(

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
