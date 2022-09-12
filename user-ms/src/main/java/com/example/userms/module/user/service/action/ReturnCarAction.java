package com.example.userms.module.user.service.action;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ReturnCarAction(
        @NotNull
        UUID rentCarId
) {
}
