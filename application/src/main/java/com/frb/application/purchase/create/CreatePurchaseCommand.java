package com.frb.application.purchase.create;

import java.time.LocalDate;

public record CreatePurchaseCommand(
        String description,
        LocalDate purchaseDate,
        Double amount
) {
    public static CreatePurchaseCommand with(
            final String description,
            final LocalDate purchaseDate,
            final Double amount
    ) {
        return new CreatePurchaseCommand(description, purchaseDate, amount);
    }
}
