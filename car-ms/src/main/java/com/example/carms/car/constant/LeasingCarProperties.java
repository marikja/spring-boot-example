package com.example.carms.car.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "car.leasing")
public class LeasingCarProperties {

    @NotEmpty
    private final Map<CarType, @NotNull @Positive BigDecimal> coefficients;

    public BigDecimal getCoefficient(CarType carType) {
        return coefficients.get(carType);
    }

}
