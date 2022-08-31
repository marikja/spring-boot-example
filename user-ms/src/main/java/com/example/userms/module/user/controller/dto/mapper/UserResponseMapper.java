package com.example.userms.module.user.controller.dto.mapper;

import com.example.userms.module.user.controller.dto.response.UserResponse;
import com.example.userms.module.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResponseMapper {

    public UserResponse map(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getUpdatedAt(),
                user.getCreatedAt()
        );
    }

    public List<UserResponse> map(List<User> users) {
        return users.stream()
                .map(this::map)
                .toList();
    }
}
