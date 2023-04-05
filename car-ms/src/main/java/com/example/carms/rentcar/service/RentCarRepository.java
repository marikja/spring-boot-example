package com.example.carms.rentcar.service;

import com.example.carms.rentcar.entity.RentCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

interface RentCarRepository extends JpaRepository<RentCar, UUID> {

    List<RentCar> findAllByUserId(UUID userId);

    List<RentCar> findAllByUserIdIn(Set<UUID> userIds);

    List<RentCar> findAllByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
