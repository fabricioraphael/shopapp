package com.frb.infrastructure.models;

import com.frb.infrastructure.JacksonTest;
import com.frb.infrastructure.purchase.models.PurchaseRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;


@JacksonTest
public class PurchaseRequestTest {

    @Autowired
    private JacksonTester<PurchaseRequest> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = Double.valueOf(11.47);

        final var request =
                new PurchaseRequest(expectedDescription, purchaseDate, amount);

        final var actualJson = this.json.write(request);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.description", expectedDescription)
                .hasJsonPathValue("$.purchaseDate", purchaseDate)
                .hasJsonPathValue("$.amount", amount);
    }

    @Test
    public void testUnmarshall() throws Exception {
        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = Double.valueOf(11.47);

        final var json = """
                {
                  "description": "%s",
                  "purchaseDate": "%s",
                  "amount": %s
                }
                """.formatted(expectedDescription, purchaseDate, amount);

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("description", expectedDescription)
                .hasFieldOrPropertyWithValue("purchaseDate", purchaseDate)
                .hasFieldOrPropertyWithValue("amount", amount);
    }
}
