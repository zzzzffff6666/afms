package com.bjtu.afms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableScheduling
@EnableWebMvc
@SpringBootApplication
@Slf4j
public class AfmsApplication {

    public static void main(String[] args) {
        log.info("log4j2");
        SpringApplication.run(AfmsApplication.class, args);
    }

}
