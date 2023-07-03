package com.ds.avengers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryService {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryService.class, args);
    }
}
