package com.coolminds.travelbridge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TravelBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelBridgeApplication.class, args);
    }
}
