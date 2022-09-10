package com.example.userms.common.integrator.car.model.request;

import java.util.Set;
import java.util.UUID;

public record FindAllRentCarsByUserIdsRequest(
        Set<UUID> userIds
) {
}
