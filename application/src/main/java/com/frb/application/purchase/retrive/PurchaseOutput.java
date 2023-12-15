package com.frb.application.purchase.retrive;

import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseID;

import java.time.LocalDate;

public record PurchaseOutput (
        PurchaseID id,
        String description,
        LocalDate purchaseDate,
        Double originalAmount,
        Double exchangeRate,
        Double convertedAmount
) {
    public static PurchaseOutput from(final Purchase aPurchase) {
        return new PurchaseOutput(
                aPurchase.getId(),
                aPurchase.getDescription(),
                aPurchase.getPurchaseDate(),
                aPurchase.getAmount().getValueRounded(),
                null,
                null
        );
    }

    public static PurchaseOutput withRate(final Purchase aPurchase, final Double exchangeRate, final Double convertedAmount) {
        return new PurchaseOutput(
                aPurchase.getId(),
                aPurchase.getDescription(),
                aPurchase.getPurchaseDate(),
                aPurchase.getAmount().getValueRounded(),
                exchangeRate,
                convertedAmount
        );
    }
}
