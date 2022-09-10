package com.example.userms.module.user.controller;

import com.example.userms.ControllerTest;
import com.example.userms.module.user.controller.dto.mapper.UserResponseMapper;
import com.example.userms.module.user.controller.dto.response.UserResponse;
import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.service.UserFinderService;
import com.example.userms.module.user.service.UserService;

import com.example.userms.module.user.service.command.CreateUserCommand;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static com.example.userms.util.ResourceLoaderUtil.asString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    void beforeAll() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Value("classpath:controller/createUserRequest.json")
    private Resource createUserRequest;

    @MockBean
    private UserService userService;

    @MockBean
    private UserResponseMapper userResponseMapper;

    @MockBean
    private UserFinderService userFinderService;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(
                userService,
                userResponseMapper,
                userFinderService
        );
    }

    private final static User USER = new User();
    static {
        USER.setFirstName("Jan");
        USER.setLastName("Novak");
        USER.setEmail("Jan.Novak@gmail.com");
        USER.setPassword("SpringIsBest");
        USER.setAge(27);
    }

    private final static UserResponse USER_RESPONSE;
    static {
        USER_RESPONSE = new UserResponse(
                USER.getId(),
                USER.getFirstName(),
                USER.getLastName(),
                USER.getEmail(),
                USER.getAge(),
                USER.getCreatedAt(),
                USER.getUpdatedAt(),
                Collections.emptyList()
        );
    }

    @Test
    @SneakyThrows
    void testSingUp() {
        when(userService.create(any())).thenReturn(USER);
        when(userResponseMapper.map(USER)).thenReturn(USER_RESPONSE);

        mockMvc.perform(post("/users/sign-up")
                .content(asString(createUserRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(USER.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(USER.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(USER.getLastName())))
                .andExpect(jsonPath("$.firstName", is(USER.getFirstName())))
                .andExpect(jsonPath("$.email", is(USER.getEmail())))
                .andExpect(jsonPath("$.age", is(USER.getAge())));


        verify(userService).create(new CreateUserCommand(
                USER.getFirstName(),
                USER.getLastName(),
                USER.getEmail(),
                USER.getPassword(),
                USER.getAge()
        ));
        verify(userResponseMapper).map(USER);
    }
}
