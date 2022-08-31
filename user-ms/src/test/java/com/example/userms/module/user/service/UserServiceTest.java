package com.example.userms.module.user.service;

import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.service.command.CreateUserCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    private final static CreateUserCommand CREATE_USER_COMMAND;

    static {
        CREATE_USER_COMMAND = new CreateUserCommand(
                "firstName",
                "lastName",
                "blem.blem@blem.com",
                "password",
                27
        );
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testCreateUser_notExist_shouldCreateUser() {
        when(userRepository.existsByEmail(any())).thenReturn(false);

        userService.create(CREATE_USER_COMMAND);

        verify(userRepository).existsByEmail(CREATE_USER_COMMAND.email());
        final ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        final User user = argumentCaptor.getValue();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(CREATE_USER_COMMAND.firstName());
        assertThat(user.getLastName()).isEqualTo(CREATE_USER_COMMAND.lastName());
        assertThat(user.getEmail()).isEqualTo(CREATE_USER_COMMAND.email());
        assertThat(user.getPassword()).isEqualTo(CREATE_USER_COMMAND.password());
        assertThat(user.getAge()).isEqualTo(CREATE_USER_COMMAND.age());
    }
}
