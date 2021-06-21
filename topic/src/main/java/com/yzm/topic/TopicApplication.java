package com.yzm.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicApplication.class, args);
    }

}
