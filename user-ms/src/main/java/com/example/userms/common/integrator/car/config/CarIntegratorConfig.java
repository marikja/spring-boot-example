package com.example.userms.common.integrator.car.config;

import com.example.userms.common.integrator.car.constant.CarIntegratorProperties;
import com.proto.rentcar.CarServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CarIntegratorConfig {

    private final CarIntegratorProperties carIntegratorProperties;

    @Bean
    @SneakyThrows
    public CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub() {
        return CarServiceGrpc.newBlockingStub(
                ManagedChannelBuilder
                        .forAddress(carIntegratorProperties.getAddress(), carIntegratorProperties.getPort())
                        .usePlaintext()
                        .build()
        );
    }
}
