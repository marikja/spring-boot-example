package com.example.carms.car.service;

import com.example.carms.car.service.model.RentCarModel;
import com.example.carms.common.grpc.util.RentCarGrpcMapperUtil;
import com.example.carms.common.util.LocalDateTimeFormatterUtil;
import com.example.carms.car.entity.Car;
import com.example.carms.rentcar.entity.RentCar;
import com.example.carms.rentcar.service.RentCarFinderService;
import com.example.carms.rentcar.service.RentCarService;
import com.example.carms.rentcar.service.action.CreateRentCarAction;
import com.example.carms.rentcar.service.action.ReturnCarAction;
import com.google.protobuf.Empty;
import com.proto.rentcar.CarServiceGrpc;
import com.proto.rentcar.FindAllByUserIdGrpcRequest;
import com.proto.rentcar.FindAllByUserIdsGrpcRequest;
import com.proto.rentcar.RentCarGrpcRequest;
import com.proto.rentcar.RentCarGrpcResponse;
import com.proto.rentcar.RentCarsGrpcResponse;
import com.proto.rentcar.ReturnCarGrpcRequest;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceGrpcImpl extends CarServiceGrpc.CarServiceImplBase {

    private final RentCarService rentCarService;
    private final CarFinderService carFinderService;
    private final RentCarFinderService rentCarFinderService;

    @Override
    public void rentCar(RentCarGrpcRequest request, StreamObserver<RentCarGrpcResponse> responseObserver) {
        final Optional<Car> carOptional = carFinderService.findById(UUID.fromString(request.getCarId()));
        if (carOptional.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Car not found.")
                    .augmentDescription(request.getCarId())
                    .asRuntimeException());
            return;
        }

        final RentCar rentCar = rentCarService.create(
                new CreateRentCarAction(
                        UUID.fromString(request.getCarId()),
                        UUID.fromString(request.getUserId()),
                        LocalDateTimeFormatterUtil.fromString(request.getFromDate()),
                        LocalDateTimeFormatterUtil.fromString(request.getToDate())
                )
        );

        final Car car = carOptional.get();
        responseObserver.onNext(RentCarGrpcMapperUtil.map(rentCar, car));
        responseObserver.onCompleted();
    }

    @Override
    public void returnCar(ReturnCarGrpcRequest request, StreamObserver<Empty> responseObserver) {
        final UUID rentCarId = UUID.fromString(request.getRentCarId());
        if (!rentCarFinderService.existById(rentCarId)) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("RentCar not found.")
                    .augmentDescription(request.getRentCarId())
                    .asRuntimeException());
            return;
        }

        rentCarService.returnCar(new ReturnCarAction(rentCarId));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAllByUserId(FindAllByUserIdGrpcRequest request, StreamObserver<RentCarsGrpcResponse> responseObserver) {
        final Map<UUID, RentCar> carIdRentCarMap = rentCarFinderService.findAllByUserId(UUID.fromString(request.getUserId())).stream()
                .collect(Collectors.toMap(RentCar::getCarId, Function.identity()));
        final List<RentCarModel> models = carFinderService.findAllByIds(carIdRentCarMap.keySet()).stream()
                .map(car -> new RentCarModel(car, carIdRentCarMap.get(car.getId())))
                .toList();

        responseObserver.onNext(RentCarGrpcMapperUtil.map(models));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllByUserIds(FindAllByUserIdsGrpcRequest request, StreamObserver<RentCarsGrpcResponse> responseObserver) {
        final Set<UUID> userIds = request.getUserIdList().stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        final Map<UUID, RentCar> carIdRentCarMap = rentCarFinderService.findAllByUserIds(userIds).stream()
                .collect(Collectors.toMap(RentCar::getCarId, Function.identity()));
        final List<RentCarModel> models = carFinderService.findAllByIds(carIdRentCarMap.keySet()).stream()
                .map(car -> new RentCarModel(car, carIdRentCarMap.get(car.getId())))
                .toList();

        responseObserver.onNext(RentCarGrpcMapperUtil.map(models));
        responseObserver.onCompleted();
    }
}
