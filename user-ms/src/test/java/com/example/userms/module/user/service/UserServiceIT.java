package com.example.userms.module.user.service;

import com.example.userms.IT;
import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.service.command.CreateUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IT
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreate() {
        final CreateUserCommand createUserCommand = new CreateUserCommand(
                "firstName",
                "lastName",
                "blem.blem@blem.com",
                "password",
                27
        );
        User user = userService.create(createUserCommand);
        user = userRepository.findById(user.getId()).get();

        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(createUserCommand.firstName());
        assertThat(user.getLastName()).isEqualTo(createUserCommand.lastName());
        assertThat(user.getEmail()).isEqualTo(createUserCommand.email());
        assertThat(user.getPassword()).isEqualTo(createUserCommand.password());
        assertThat(user.getAge()).isEqualTo(createUserCommand.age());
    }
}
