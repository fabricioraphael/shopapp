package com.frb.domain.purchase;

import com.frb.domain.exceptions.DomainException;
import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseAmount;
import com.frb.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseTest {

    @Test
    public void givenAValidParams_whenCallNewPurchase_thenInstantiateAPurchase() {
        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(11.47));

        final var actualPurchase =
                Purchase.newPurchase(expectedDescription, purchaseDate, amount);

        Assertions.assertNotNull(actualPurchase);
        Assertions.assertNotNull(actualPurchase.getId());
        Assertions.assertEquals(expectedDescription, actualPurchase.getDescription());
        Assertions.assertEquals(purchaseDate, actualPurchase.getPurchaseDate());
        Assertions.assertEquals(amount, actualPurchase.getAmount());
    }

    @Test
    public void givenAnInvalidAmount_whenCallNewPurchaseAndValidate_thenShouldReceiveError() {
        final var expectedErrorMessage = "'amount' must be a valid positive amount";
        final var expectedErrorCount = 1;
        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(-3.27));

        final var actualPurchase =
                Purchase.newPurchase(expectedDescription, purchaseDate, amount);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualPurchase.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidPurchaseDate_whenCallNewPurchaseAndValidate_thenShouldReceiveError() {
        final var expectedDescription = "Purchase Description";
        final LocalDate purchaseDate = null;
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(11.47));

        Assertions.assertThrows(NullPointerException.class, () -> Purchase.newPurchase(expectedDescription, purchaseDate, amount));
    }

    @Test
    public void givenAnInvalidDescriptionLengthMoreThan50_whenCallNewPurchaseAndValidate_thenShouldReceiveError() {
        final var expectedDescription = """
                description more than 50 caracteres & description more than 50 caracteres
                """;
        final LocalDate purchaseDate = LocalDate.now();
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(11.36));

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' must be less than 50 characters";

        final var actualPurchase =
                Purchase.newPurchase(expectedDescription, purchaseDate, amount);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualPurchase.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewPurchaseAndValidate_thenShouldReceiveOK() {
        final var expectedDescription = " ";
        final var purchaseDate = LocalDate.now();
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(11.47));

        final var actualPurchase =
                Purchase.newPurchase(expectedDescription, purchaseDate, amount);

        Assertions.assertDoesNotThrow(() -> actualPurchase.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualPurchase);
        Assertions.assertNotNull(actualPurchase.getId());
        Assertions.assertEquals(expectedDescription, actualPurchase.getDescription());
        Assertions.assertEquals(purchaseDate, actualPurchase.getPurchaseDate());
        Assertions.assertEquals(amount, actualPurchase.getAmount());
    }
}
