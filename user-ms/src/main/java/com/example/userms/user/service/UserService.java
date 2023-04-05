package com.example.userms.user.service;

import com.example.userms.common.constant.DbTable;
import com.example.userms.common.integrator.car.CarIntegrator;
import com.example.userms.common.integrator.car.model.request.RentCarRequest;
import com.example.userms.common.integrator.car.model.request.ReturnCarRequest;
import com.example.userms.common.integrator.car.model.response.RentCarResponse;
import com.example.userms.common.service.PostgresLockService;
import com.example.userms.user.entity.User;
import com.example.userms.user.exception.UserAlreadyExistsException;
import com.example.userms.user.exception.UserNotFoundException;
import com.example.userms.user.service.action.CreateUserAction;
import com.example.userms.user.service.action.RentCarAction;
import com.example.userms.user.service.action.ReturnCarAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CarIntegrator carIntegrator;
    private final PostgresLockService postgresLockService;

    @Transactional
    public User create(@Valid CreateUserAction action) {
        postgresLockService.lock(DbTable.USER, List.of(action.email()));

        if (userRepository.existsByEmail(action.email())) {
            throw new UserAlreadyExistsException();
        }

        final User user = new User();
        user.setFirstName(action.firstName());
        user.setLastName(action.lastName());
        user.setPassword(action.password());
        user.setBirth(action.birth());
        user.setEmail(action.email());
        return userRepository.save(user);
    }

    public RentCarResponse rentCar(@Valid RentCarAction action) {
        if (!userRepository.existsById(action.userId())) {
            throw new UserNotFoundException();
        }

        return carIntegrator.rentCar(
                new RentCarRequest(
                        action.userId(),
                        action.carId(),
                        action.fromDate(),
                        action.toDate()
                ));
    }

    public void returnCar(@Valid ReturnCarAction action) {
        carIntegrator.returnCar(new ReturnCarRequest(action.rentCarId()));
    }
}
