package com.frb.infrastructure.purchase.presenters;

import com.frb.application.purchase.retrive.PurchaseOutput;
import com.frb.infrastructure.purchase.models.PurchaseResponse;

public interface PurchaseApiPresenter  {
    static PurchaseResponse present(final PurchaseOutput output) {
        return new PurchaseResponse(
                output.id().getValue(),
                output.description(),
                output.purchaseDate(),
                output.originalAmount(),
                output.exchangeRate(),
                output.convertedAmount()
        );
    }
}