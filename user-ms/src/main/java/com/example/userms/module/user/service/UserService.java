package com.example.userms.module.user.service;

import com.example.userms.common.integrator.car.CarIntegrator;
import com.example.userms.common.integrator.car.model.request.RentCarRequest;
import com.example.userms.common.integrator.car.model.request.ReturnCarRequest;
import com.example.userms.common.integrator.car.model.response.RentCarResponse;
import com.example.userms.module.user.entity.User;
import com.example.userms.module.user.exception.UserAlreadyExistsException;
import com.example.userms.module.user.exception.UserNotFoundException;
import com.example.userms.module.user.service.command.CreateUserCommand;
import com.example.userms.module.user.service.command.RentCarCommand;
import com.example.userms.module.user.service.command.ReturnCarCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CarIntegrator carIntegrator;

    @Transactional
    public User create(@Valid CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
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

    public RentCarResponse rentCar(@Valid RentCarCommand command) {
        if (!userRepository.existsById(command.userId())) {
            throw new UserNotFoundException();
        }

        return carIntegrator.rentCar(
                new RentCarRequest(
                        command.userId(),
                        command.carId(),
                        command.fromDate(),
                        command.toDate()
                ));
    }

    public void returnCar(@Valid ReturnCarCommand command) {
        carIntegrator.returnCar(new ReturnCarRequest(command.rentCarId()));
    }
}
