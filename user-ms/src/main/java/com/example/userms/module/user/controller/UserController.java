package com.example.userms.module.user.controller;

import com.example.userms.common.dto.response.PageModel;
import com.example.userms.common.util.PageMapperUtil;
import com.example.userms.module.user.controller.dto.mapper.UserResponseMapper;
import com.example.userms.module.user.controller.dto.request.CreateUserRequest;
import com.example.userms.module.user.controller.dto.response.UserResponse;
import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.service.UserFinderService;
import com.example.userms.module.user.service.UserService;
import com.example.userms.module.user.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserFinderService userFinderService;
    private final UserResponseMapper userResponseMapper;

    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable UUID userId) {
        return userResponseMapper.map(userFinderService.getById(userId));
    }

    @GetMapping("/search")
    public PageModel<UserResponse> search(
            @PageableDefault @SortDefault(sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        final Page<User> users = userFinderService.search(pageable);
        final List<UserResponse> response = userResponseMapper.map(users.getContent());
        return PageMapperUtil.map(users, response);
    }

    @PostMapping("/sign-up")
    public UserResponse signUp(@RequestBody CreateUserRequest request) {
        final User user = userService.create(
                new CreateUserCommand(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.password(),
                        request.age()
                )
        );
        return userResponseMapper.map(user);
    }

    @PostMapping("/sign-in")
    public void signIn() {
        //TODO
    }

    @PostMapping("/{userId}/cars/{carId}/buy")
    public void buyCar(@PathVariable UUID userId, @PathVariable UUID carId) {
        //TODO
    }

    @PostMapping("/{userId}/cars/{carId}/sell")
    public void sellCar(@PathVariable UUID userId, @PathVariable UUID carId) {
        //TODO
    }

    @GetMapping("/{userId}/logs")
    public void getLogUser(@PathVariable UUID userId) {
        //TODO
    }
}