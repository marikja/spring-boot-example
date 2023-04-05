package com.example.carms.rentcar.service.action;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ReturnCarAction(
        @NotNull
        UUID rentCarId
) {
}
