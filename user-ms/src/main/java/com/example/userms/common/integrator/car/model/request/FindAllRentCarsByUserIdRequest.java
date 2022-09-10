package com.example.userms.common.integrator.car.model.request;

import java.util.UUID;

public record FindAllRentCarsByUserIdRequest(
        UUID userId
) {
}
