package com.frb.domain.purchase;

import com.frb.domain.purchase.PurchaseAmount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PurchaseAmountTest {

    @Test
    public void givenAValidParams_whenCallNewPurchaseAmount_thenInstantiateAPurchaseAmount() {
        final var expectedAmount = BigDecimal.valueOf(11.47);
        final var purchaseAmount = PurchaseAmount.from(expectedAmount);

        Assertions.assertNotNull(purchaseAmount);
        Assertions.assertNotNull(purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmount, purchaseAmount.getValue());
    }

    @Test
    public void givenABigDoubleAmount_whenCallNewPurchaseAmount_thenRoudedAPurchaseAmount() {
        final var expectedAmount = BigDecimal.valueOf(11.479999999);
        final var expectedAmountRounded = Double.valueOf(11.48);
        final var purchaseAmount = PurchaseAmount.from(expectedAmount);

        Assertions.assertNotNull(purchaseAmount);
        Assertions.assertNotNull(purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmount, purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmountRounded, purchaseAmount.getValueRounded());
    }

    @Test
    public void givenARate_whenCallCalcRate_thenAmountWithRateApplied() {
        final var expectedAmount = BigDecimal.valueOf(10.0);
        final var rate = Double.valueOf(1.5);
        final var expectedAmountWithRate = Double.valueOf(15.0);

        final var purchaseAmount = PurchaseAmount.from(expectedAmount);

        Assertions.assertNotNull(purchaseAmount);
        Assertions.assertNotNull(purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmount, purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmountWithRate, purchaseAmount.calcWithRateRounded(rate));
    }

    @Test
    public void givenARate_whenCallCalcRate_thenAmountWithRateAppliedAndRouded() {
//        12.42 * 5.033 = 62.50986
        final var expectedAmount = BigDecimal.valueOf(12.42);
        final var rate = Double.valueOf(5.033);
        final var expectedAmountWithRate = Double.valueOf(62.51);

        final var purchaseAmount = PurchaseAmount.from(expectedAmount);

        Assertions.assertNotNull(purchaseAmount);
        Assertions.assertNotNull(purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmount, purchaseAmount.getValue());
        Assertions.assertEquals(expectedAmountWithRate, purchaseAmount.calcWithRateRounded(rate));
    }
}
