package com.example.carms.car.model;

import com.example.carms.car.constant.CarType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Car {

    @Id
    @EqualsAndHashCode.Include
    @Column(updatable = false, unique = true)
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false, updatable = false, unique = true)
    private String vin;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer horsePower;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarType type;

    @Nullable
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
