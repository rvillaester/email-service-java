package com.emailservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Slf4j
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.info("GG");
        SpringApplication.run(Application.class, args);
    }
}
