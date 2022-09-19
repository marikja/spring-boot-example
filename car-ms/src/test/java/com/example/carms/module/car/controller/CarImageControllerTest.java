package com.example.carms.module.car.controller;

import com.example.carms.ControllerTest;
import com.example.carms.module.car.constant.CarType;
import com.example.carms.module.car.controller.dto.mapper.CarResponseMapper;
import com.example.carms.module.car.controller.dto.response.CarResponse;
import com.example.carms.module.car.entity.Car;
import com.example.carms.module.car.service.CarFinderService;
import com.example.carms.module.car.service.CarService;
import com.example.carms.module.car.service.action.CreateCarAction;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.example.carms.util.ResourceLoaderUtil.asString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@WebMvcTest(CarController.class)
public class CarImageControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    void beforeAll() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Value("classpath:controller/createCarRequest.json")
    private Resource createCarRequest;

    @MockBean
    private CarService carService;

    @MockBean
    private CarResponseMapper carResponseMapper;

    @MockBean
    private CarFinderService carFinderService;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(
                carService,
                carResponseMapper,
                carFinderService
        );
    }

    private final static Car CAR;
    static {
        CAR = new Car();
        CAR.setVin("vin");
        CAR.setMake("make");
        CAR.setModel("model");
        CAR.setHorsePower(400);
        CAR.setType(CarType.CABRIOLET);
        CAR.setPrice(BigDecimal.valueOf(40000L));
    }

    private final static CarResponse CAR_RESPONSE;
    static {
        CAR_RESPONSE = new CarResponse(
                CAR.getId(),
                CAR.getVin(),
                CAR.getMake(),
                CAR.getModel(),
                CAR.getHorsePower(),
                CAR.getType(),
                CAR.getPrice()
        );
    }

    @Test
    @SneakyThrows
    void testCreate_shouldPass() {
        when(carService.create(any())).thenReturn(CAR);
        when(carResponseMapper.map(CAR)).thenReturn(CAR_RESPONSE);

        mockMvc.perform(post("/cars")
                        .content(asString(createCarRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin", is(CAR.getVin())))
                .andExpect(jsonPath("$.make", is(CAR.getMake())))
                .andExpect(jsonPath("$.model", is(CAR.getModel())))
                .andExpect(jsonPath("$.horsePower", is(CAR.getHorsePower())))
                .andExpect(jsonPath("$.type", is(CAR.getType().name())))
                .andExpect(jsonPath("$.price", is(CAR.getPrice().intValue())));

        verify(carService).create(new CreateCarAction(
                CAR.getVin(),
                CAR.getMake(),
                CAR.getModel(),
                CAR.getHorsePower(),
                CAR.getType(),
                CAR.getPrice()
        ));
        verify(carResponseMapper).map(CAR);
    }

    @Test
    @SneakyThrows
    void testSearch_defaultSearch_shouldPass() {
        final Page<Car> cars = new PageImpl<>(List.of(CAR));
        when(carFinderService.search(any())).thenReturn(cars);
        when(carResponseMapper.map(any(List.class))).thenReturn(List.of(CAR_RESPONSE));

        mockMvc.perform(get("/cars/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.elements", is(1)))
                .andExpect(jsonPath("$.pages", is(1)))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].vin", is(CAR.getVin())))
                .andExpect(jsonPath("$.content[0].make", is(CAR.getMake())))
                .andExpect(jsonPath("$.content[0].model", is(CAR.getModel())))
                .andExpect(jsonPath("$.content[0].horsePower", is(CAR.getHorsePower())))
                .andExpect(jsonPath("$.content[0].type", is(CAR.getType().name())))
                .andExpect(jsonPath("$.content[0].price", is(CAR.getPrice().intValue())));

        verify(carFinderService).search(Pageable.ofSize(10));
        verify(carResponseMapper).map(cars.getContent());
    }

    @Test
    @SneakyThrows
    void testGetById_getExistingCar_shouldPass() {
        final UUID uuid = UUID.randomUUID();
        when(carFinderService.getById(any())).thenReturn(CAR);
        when(carResponseMapper.map(any(Car.class))).thenReturn(CAR_RESPONSE);

        mockMvc.perform(get("/cars/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin", is(CAR.getVin())))
                .andExpect(jsonPath("$.make", is(CAR.getMake())))
                .andExpect(jsonPath("$.model", is(CAR.getModel())))
                .andExpect(jsonPath("$.horsePower", is(CAR.getHorsePower())))
                .andExpect(jsonPath("$.type", is(CAR.getType().name())))
                .andExpect(jsonPath("$.price", is(CAR.getPrice().intValue())));

        verify(carFinderService).getById(uuid);
        verify(carResponseMapper).map(CAR);
    }
}
