package com.frb.application.purchase.create;

import com.frb.domain.purchase.Purchase;

import java.time.LocalDate;

public record CreatePurchaseOutput(
        String id,
        String description,
        LocalDate purchaseDate,
        Double amount
) {
    public static CreatePurchaseOutput from(final String id, final String description, final LocalDate purchaseDate, final Double amount) {
        return new CreatePurchaseOutput(id, description, purchaseDate, amount);
    }

    public static CreatePurchaseOutput from(final Purchase aPurchase) {
        return new CreatePurchaseOutput(aPurchase.getId().getValue(), aPurchase.getDescription(), aPurchase.getPurchaseDate(), aPurchase.getAmount().getValueRounded());
    }
}
