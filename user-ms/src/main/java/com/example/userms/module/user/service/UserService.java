package com.example.userms.module.user.service;

import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.exception.UserAlreadyExistsException;
import com.example.userms.module.user.exception.UserNotFoundException;
import com.example.userms.module.user.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;


@Service
@Log4j2
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(@Valid CreateUserCommand command) {
        if(userRepository.existsByEmail(command.email())) {
            log.warn("Email already exists.");
            throw new UserAlreadyExistsException();
        }

        final User user = new User();
        user.setFirstName(command.firstName());
        user.setLastName(command.lastName());
        user.setPassword(command.password());
        user.setAge(command.age());
        user.setEmail(command.email());
        return userRepository.save(user);
    }
}
