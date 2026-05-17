package com.example.eventproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventprojectApplication.class, args);
    }

}
