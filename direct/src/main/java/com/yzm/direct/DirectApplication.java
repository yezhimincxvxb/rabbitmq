package com.yzm.direct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DirectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DirectApplication.class, args);
    }

}
