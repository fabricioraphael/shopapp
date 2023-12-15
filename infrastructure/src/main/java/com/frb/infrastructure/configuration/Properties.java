package com.frb.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Properties {
    Shopapp shopapp = new Shopapp();

    public Shopapp getShopapp() {
        return shopapp;
    }

    public class Shopapp {
        Treasury treasury = new Treasury();

        public Treasury getTreasury() {
            return treasury;
        }

        public class Treasury {
            String baseUrl = "";

            public String getBaseUrl() {
                return baseUrl;
            }

            public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
            }
        }
    }
}
