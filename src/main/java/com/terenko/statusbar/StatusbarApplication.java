package com.terenko.statusbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatusbarApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusbarApplication.class, args);
    }

}
