package com.frb.infrastructure.e2e.purchase;


import com.frb.infrastructure.purchase.persistence.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.frb.infrastructure.E2ETest;
import com.frb.infrastructure.e2e.MockDsl;

import java.time.LocalDate;

@E2ETest
@Testcontainers
public class PurchaseE2ETest implements MockDsl {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Container
    private static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:latest")
            .withPassword("123456")
            .withUsername("root")
            .withDatabaseName("shopcore");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("mysql.port", () -> MYSQL_CONTAINER.getMappedPort(3306));
    }

    @Override
    public MockMvc mvc() {
        return this.mvc;
    }

//    @Test
//    public void asAShopAppShouldBeAbleToCreateANewPurchaseWithValidValues() throws Exception {
//        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
//        Assertions.assertEquals(0, purchaseRepository.count());
//
//        final var expectedDescription = "Purchase Description";
//        final var expectedPurchaseDate = LocalDate.now();
//        final var expectedAmount = Double.valueOf(11.47);
//
//        final var actualId = givenAPurchase(expectedDescription, expectedPurchaseDate, expectedAmount);
//
//        final var actualPurchase = purchaseRepository.findById(actualId.getValue()).get();
//        Assertions.assertEquals(expectedDescription, actualPurchase.getDescription());
//        Assertions.assertEquals(expectedPurchaseDate, actualPurchase.getPurchaseDate());
//        Assertions.assertEquals(expectedAmount, actualPurchase.getAmount());
//    }
}
