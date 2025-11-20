package io.akikr.demopostgredbapp;

import io.akikr.demopostgredbapp.config.AppLoggingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(value = {AppLoggingProperties.class})
public class DemoPostgreDbApp {

    private static final Logger log = LoggerFactory.getLogger(DemoPostgreDbApp.class);

    public static void main(String[] args) {
        log.info("Starting application with args:[{}]", Arrays.toString(args));
        SpringApplication.run(DemoPostgreDbApp.class, args);
        log.info("Completed execution of main method");
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("Application started with command-line arguments: {}", Arrays.toString(args));
            log.info("Application started successfully !!");
        };
    }
}
