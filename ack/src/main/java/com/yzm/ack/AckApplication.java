package com.yzm.ack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AckApplication {

    public static void main(String[] args) {
        SpringApplication.run(AckApplication.class, args);
    }

}
