package com.frb.application.purchase.create;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePurchaseCommand(
        String description,
        LocalDate purchaseDate,
        BigDecimal amount
) {
    public static CreatePurchaseCommand with(
            final String description,
            final LocalDate purchaseDate,
            final BigDecimal amount
    ) {
        return new CreatePurchaseCommand(description, purchaseDate, amount);
    }
}
