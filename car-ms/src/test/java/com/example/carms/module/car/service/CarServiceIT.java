package com.example.carms.module.car.service;

import com.example.carms.IT;
import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.action.CreateCarAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@IT
public class CarServiceIT {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    void testCreate() {
        final CreateCarAction createCarAction = new CreateCarAction(
                "vin",
                "make",
                "model",
                400,
                CarType.CABRIOLET,
                BigDecimal.valueOf(40000L)
        );
        Car car = carService.create(createCarAction);
        car = carRepository.findById(car.getId()).get();

        assertThat(car.getId()).isNotNull();
        assertThat(car.getVin()).isEqualTo(createCarAction.vin());
        assertThat(car.getMake()).isEqualTo(createCarAction.make());
        assertThat(car.getModel()).isEqualTo(createCarAction.model());
        assertThat(car.getHorsePower()).isEqualTo(createCarAction.horsePower());
        assertThat(car.getType()).isEqualTo(createCarAction.type());
        assertThat(car.getPrice()).isEqualTo(createCarAction.price());
    }
}
