package com.example.carms.rentcar.service.cron;

import com.example.carms.car.entity.Car;
import com.example.carms.car.service.CarFinderService;
import com.example.carms.rentcar.entity.RentCar;
import com.example.carms.rentcar.service.RentCarFinderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class RentCarDailyCron {

    private final RentCarFinderService rentCarFinderService;
    private final CarFinderService carFinderService;

    @Scheduled(cron = "${cron.leasing.time}")
    public void dailyReport() {
        final List<RentCar> rentCars = rentCarFinderService.findAllByCreatedAtToday();
        log.info("Today {} cars were rent.", rentCars.size());
        if (rentCars.isEmpty()) {
            return;
        }

        final Set<UUID> carIds = rentCars.stream()
                .map(RentCar::getCarId)
                .collect(Collectors.toSet());
        final List<Car> cars = carFinderService.findAllByIds(carIds);

        BigDecimal max = BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for (Car car: cars) {
            final BigDecimal carPrice = car.getPrice();

            if (max.compareTo(carPrice) < 0) {
                max = carPrice;
            }
            sum = sum.add(carPrice);
        }

        log.info("The max price was {}.", max);
        log.info("The avg price was {}", sum.divide(BigDecimal.valueOf(cars.size())));
    }
}
