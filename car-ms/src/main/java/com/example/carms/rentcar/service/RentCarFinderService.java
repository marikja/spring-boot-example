package com.example.carms.rentcar.service;

import com.example.carms.rentcar.entity.RentCar;
import com.example.carms.rentcar.exception.RentCarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<RentCar> findAllByCreatedAtToday() {
        final LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        return rentCarRepository.findAllByCreatedAtBetween(startOfDay, startOfDay.plusDays(1).minusSeconds(1));
    }
}
