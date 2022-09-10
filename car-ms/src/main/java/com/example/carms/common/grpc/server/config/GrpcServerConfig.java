package com.example.carms.common.grpc.server.config;

import com.example.carms.common.grpc.server.GrpcServer;
import com.example.carms.common.grpc.server.constant.GrpcServerProperties;
import io.grpc.BindableService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    private final GrpcServerProperties grpcServerProperties;

    @Bean
    public GrpcServer grpcServer(List<BindableService> bindableServices) {
        return new GrpcServer(grpcServerProperties.getPort(), bindableServices);
    }
}
