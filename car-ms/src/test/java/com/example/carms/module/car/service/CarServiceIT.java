package com.example.carms.module.car.service;

import com.example.carms.IT;
import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.command.CreateCarCommand;
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
        final CreateCarCommand createCarCommand = new CreateCarCommand(
                "vin",
                "make",
                "model",
                400,
                CarType.CABRIOLET,
                BigDecimal.valueOf(40000L)
        );
        Car car = carService.create(createCarCommand);
        car = carRepository.findById(car.getId()).get();

        assertThat(car.getId()).isNotNull();
        assertThat(car.getVin()).isEqualTo(createCarCommand.vin());
        assertThat(car.getMake()).isEqualTo(createCarCommand.make());
        assertThat(car.getModel()).isEqualTo(createCarCommand.model());
        assertThat(car.getHorsePower()).isEqualTo(createCarCommand.horsePower());
        assertThat(car.getType()).isEqualTo(createCarCommand.type());
        assertThat(car.getPrice()).isEqualTo(createCarCommand.price());
    }
}
