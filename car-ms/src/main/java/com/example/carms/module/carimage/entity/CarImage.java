package com.example.carms.module.carimage.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarImage {

    @Id
    @EqualsAndHashCode.Include
    @Column(updatable = false, unique = true)
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false, updatable = false)
    private UUID carId;

    @Column(nullable = false, updatable = false)
    private String imageName;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public CarImage(UUID carId, String imageName) {
        this.carId = carId;
        this.imageName = imageName;
    }
}
