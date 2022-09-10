package com.example.userms.module.user.service.command;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ReturnCarCommand(
        @NotNull
        UUID rentCarId
) {
}
