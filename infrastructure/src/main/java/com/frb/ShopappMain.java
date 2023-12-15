package com.frb;

import com.frb.infrastructure.configuration.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Properties.class)
public class ShopappMain {
    public static void main(String[] args) {
        SpringApplication.run(ShopappMain.class, args);
    }
}