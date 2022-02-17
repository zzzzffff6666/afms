package com.bjtu.afms;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableScheduling
@EnableWebMvc
@SpringBootApplication
@MapperScan("com.bjtu.afms.mapper")
@Slf4j
public class AfmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfmsApplication.class, args);
    }

}
