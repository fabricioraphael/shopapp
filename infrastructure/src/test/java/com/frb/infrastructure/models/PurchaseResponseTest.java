package com.frb.infrastructure.models;

import com.frb.infrastructure.JacksonTest;
import com.frb.infrastructure.purchase.models.PurchaseResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import java.time.LocalDate;

@JacksonTest
public class PurchaseResponseTest {
        @Autowired
        private JacksonTester<PurchaseResponse> json;

        @Test
        public void testMarshall() throws Exception {
            final var expectedId = "123";
            final var expectedDescription = "Purchase Description";
            final var expectedPurchaseDate = LocalDate.now();
            final var expectedOriginalAmount = Double.valueOf(11.47);
            final var expectedConversionRate = Double.valueOf(1.27);
            final var expectedConversionAmount = Double.valueOf(14.56);

            final var response = new PurchaseResponse(
                    expectedId,
                    expectedDescription,
                    expectedPurchaseDate,
                    expectedOriginalAmount,
                    expectedConversionRate,
                    expectedConversionAmount
            );

            final var actualJson = this.json.write(response);

            Assertions.assertThat(actualJson)
                    .hasJsonPathValue("$.id", expectedId)
                    .hasJsonPathValue("$.description", expectedDescription)
                    .hasJsonPathValue("$.purchaseDate", expectedPurchaseDate)
                    .hasJsonPathValue("$.originalAmount", expectedOriginalAmount)
                    .hasJsonPathValue("$.exchangeRate", expectedConversionRate)
                    .hasJsonPathValue("$.convertedAmount", expectedOriginalAmount);
        }

        @Test
        public void testUnmarshall() throws Exception {
            final var expectedId = "123";
            final var expectedDescription = "Purchase Description";
            final var expectedPurchaseDate = LocalDate.now();
            final var expectedOriginalAmount = Double.valueOf(11.47);
            final var expectedConversionRate = Double.valueOf(1.27);
            final var expectedConversionAmount = Double.valueOf(14.56);

            final var json = """
                {
                  "id": "%s",
                  "description": "%s",
                  "purchaseDate": "%s",
                  "originalAmount": "%s",
                  "exchangeRate": "%s",
                  "convertedAmount": "%s"
                } 
                """.formatted(
                            expectedId,
                            expectedDescription,
                            expectedPurchaseDate,
                            expectedOriginalAmount,
                            expectedConversionRate,
                            expectedConversionAmount
                    );

            final var actualJson = this.json.parse(json);

            Assertions.assertThat(actualJson)
                    .hasFieldOrPropertyWithValue("id", expectedId)
                    .hasFieldOrPropertyWithValue("description", expectedDescription)
                    .hasFieldOrPropertyWithValue("purchaseDate", expectedPurchaseDate)
                    .hasFieldOrPropertyWithValue("originalAmount", expectedOriginalAmount)
                    .hasFieldOrPropertyWithValue("exchangeRate", expectedConversionRate)
                    .hasFieldOrPropertyWithValue("convertedAmount", expectedConversionAmount);
        }
}
