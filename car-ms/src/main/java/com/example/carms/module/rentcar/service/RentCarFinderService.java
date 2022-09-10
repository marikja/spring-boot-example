package com.example.carms.module.rentcar.service;

import com.example.carms.module.rentcar.entity.RentCar;
import com.example.carms.module.rentcar.exception.RentCarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentCarFinderService{

    private final RentCarRepository rentCarRepository;

    public RentCar getById(UUID rentCarId) {
        return rentCarRepository.findById(rentCarId).orElseThrow(RentCarNotFoundException::new);
    }

    public boolean existById(UUID rentCarId) {
        return rentCarRepository.existsById(rentCarId);
    }

    public List<RentCar> findAllByUserId(UUID userId) {
        return rentCarRepository.findAllByUserId(userId);
    }

    public List<RentCar> findAllByUserIds(Set<UUID> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }

        return rentCarRepository.findAllByUserIdIn(userIds);
    }
}
