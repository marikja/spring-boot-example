package com.example.carms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CarMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarMsApplication.class, args);
    }

}
