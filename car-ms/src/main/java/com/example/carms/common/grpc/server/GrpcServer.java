package com.example.carms.common.grpc.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
public class GrpcServer {

    private final Server server;

    public GrpcServer(int port, List<BindableService> bindableServices) {
        ServerBuilder<?> builder = ServerBuilder.forPort(port);
        bindableServices.forEach(builder::addService);
        server = builder.build();
    }

    @SneakyThrows
    @PostConstruct
    public void start() {
        server.start();
        log.info("GRPC server started. Listening on port {}", server.getPort());
    }

    @PreDestroy
    public void stop() {
        log.info("GRPC server shutting down");
        server.shutdown();
    }
}
