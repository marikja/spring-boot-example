package com.example.carms.module.car.controller;

import com.example.carms.module.car.controller.dto.mapper.CarResponseMapper;
import com.example.carms.module.car.controller.dto.request.CalculateLeasingRequest;
import com.example.carms.module.car.controller.dto.request.CreateCarRequest;
import com.example.carms.module.car.controller.dto.response.CarResponse;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.CarFinderService;
import com.example.carms.module.car.service.command.CalculateLeasingCommand;
import com.example.carms.module.car.service.command.CreateCarCommand;
import com.example.carms.module.car.service.CarService;
import com.example.carms.common.dto.response.PageModel;
import com.example.carms.common.util.PageMapperUtil;
import com.example.carms.module.car.service.model.LeasingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CarFinderService carFinderService;

    private final CarResponseMapper carResponseMapper;

    @GetMapping("/search")
    public PageModel<CarResponse> search(@PageableDefault Pageable pageable) {
        final Page<Car> cars = carFinderService.search(pageable);
        final List<CarResponse> response = carResponseMapper.map(cars.getContent());
        return PageMapperUtil.map(cars, response);
    }

    @PostMapping
    public CarResponse create(@RequestBody CreateCarRequest createCarRequest) {
        return carResponseMapper.map(carService.create(
                new CreateCarCommand(
                        createCarRequest.vin(),
                        createCarRequest.make(),
                        createCarRequest.model(),
                        createCarRequest.horsePower(),
                        createCarRequest.type(),
                        createCarRequest.price()
                )
        ));
    }

    @GetMapping("/{carId}")
    public CarResponse getById(@PathVariable UUID carId) {
        return carResponseMapper.map(carFinderService.getById(carId));
    }

    @PostMapping("/{carId}/calculate-leasing")
    public LeasingModel calculateLeasing(@PathVariable UUID carId, @RequestBody CalculateLeasingRequest request) {
        return carService.calculateLeasing(new CalculateLeasingCommand(carId, request.monthCount()));
    }

}
