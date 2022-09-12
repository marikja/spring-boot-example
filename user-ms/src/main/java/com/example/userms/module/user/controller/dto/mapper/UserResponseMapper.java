package com.example.userms.module.user.controller.dto.mapper;

import com.example.userms.common.integrator.car.CarIntegrator;
import com.example.userms.common.integrator.car.model.request.FindAllRentCarsByUserIdRequest;
import com.example.userms.common.integrator.car.model.request.FindAllRentCarsByUserIdsRequest;
import com.example.userms.common.integrator.car.model.response.RentCarResponse;
import com.example.userms.module.user.controller.dto.response.UserResponse;
import com.example.userms.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserResponseMapper {

    private final CarIntegrator carIntegrator;

    public UserResponse map(User user) {
        return map(
                user,
                carIntegrator.findAllRentCarsByUserId(new FindAllRentCarsByUserIdRequest(user.getId()))
        );
    }

    public List<UserResponse> map(List<User> users) {
        final Set<UUID> userIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        final Map<UUID, List<RentCarResponse>> userRentCarMap = carIntegrator
                .findAllRentCarsByUserIds(new FindAllRentCarsByUserIdsRequest(userIds)).stream()
                .collect(Collectors.groupingBy(RentCarResponse::userId));

        return users.stream()
                .map(user -> map(user, userRentCarMap.get(user.getId())))
                .toList();
    }

    private static UserResponse map(User user, List<RentCarResponse> rentCars) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirth(),
                user.getUpdatedAt(),
                user.getCreatedAt(),
                rentCars
        );
    }
}
