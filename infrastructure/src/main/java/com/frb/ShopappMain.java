package com.frb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopappMain {
    public static void main(String[] args) {
        SpringApplication.run(ShopappMain.class, args);

        var app = new ApplicationDemo("appDemo");
        var domain = new DomainDemo("domainDesc");

        System.out.println("app: " + app.getName());
        System.out.println("domain: " + domain.getDescription());
    }

}