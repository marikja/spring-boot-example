package com.example.userms.module.user.service.command;


import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record CreateUserCommand(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        String email,

        @NotBlank
        String password,

        @Nullable
        Integer age

) {
}
