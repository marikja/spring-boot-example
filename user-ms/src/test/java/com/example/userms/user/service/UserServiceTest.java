package com.example.userms.user.service;

import com.example.userms.common.constant.DbTable;
import com.example.userms.common.integrator.car.CarIntegrator;
import com.example.userms.common.service.PostgresLockService;
import com.example.userms.user.entity.User;
import com.example.userms.user.service.action.CreateUserAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final CarIntegrator carIntegrator = mock(CarIntegrator.class);
    private final PostgresLockService postgresLockService = mock(PostgresLockService.class);
    private final UserService userService = new UserService(
            userRepository,
            carIntegrator,
            postgresLockService
    );

    private final static CreateUserAction CREATE_USER_ACTION;

    static {
        CREATE_USER_ACTION = new CreateUserAction(
                "firstName",
                "lastName",
                "blem.blem@blem.com",
                "password",
                null
        );
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testCreateUser_notExist_shouldCreateUser() {
        when(userRepository.existsByEmail(any())).thenReturn(false);

        userService.create(CREATE_USER_ACTION);

        verify(postgresLockService).lock(DbTable.USER, List.of(CREATE_USER_ACTION.email()));

        verify(userRepository).existsByEmail(CREATE_USER_ACTION.email());
        final ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        final User user = argumentCaptor.getValue();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(CREATE_USER_ACTION.firstName());
        assertThat(user.getLastName()).isEqualTo(CREATE_USER_ACTION.lastName());
        assertThat(user.getEmail()).isEqualTo(CREATE_USER_ACTION.email());
        assertThat(user.getPassword()).isEqualTo(CREATE_USER_ACTION.password());
        assertThat(user.getBirth()).isNull();
    }
}
