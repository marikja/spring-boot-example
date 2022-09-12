package com.example.userms.module.user.service;

import com.example.userms.IT;
import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.service.action.CreateUserAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IT
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreate() {
        final CreateUserAction createUserAction = new CreateUserAction(
                "firstName",
                "lastName",
                "blem.blem@blem.com",
                "password",
                LocalDate.now()
        );
        User user = userService.create(createUserAction);
        user = userRepository.findById(user.getId()).get();

        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(createUserAction.firstName());
        assertThat(user.getLastName()).isEqualTo(createUserAction.lastName());
        assertThat(user.getEmail()).isEqualTo(createUserAction.email());
        assertThat(user.getPassword()).isEqualTo(createUserAction.password());
        assertThat(user.getBirth()).isEqualTo(createUserAction.birth());
    }
}
