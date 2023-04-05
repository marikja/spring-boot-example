package com.example.carms.car.controller.dto.mapper;

import com.example.carms.car.controller.dto.response.CarResponse;
import com.example.carms.car.entity.Car;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarResponseMapper {

    public CarResponse map(Car car) {
        return new CarResponse(
                car.getId(),
                car.getVin(),
                car.getMake(),
                car.getModel(),
                car.getHorsePower(),
                car.getType(),
                car.getPrice()
        );
    }

    public List<CarResponse> map(List<Car> users) {
        return users.stream()
                .map(this::map)
                .toList();
    }
}
