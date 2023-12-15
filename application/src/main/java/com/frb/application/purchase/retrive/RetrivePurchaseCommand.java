package com.frb.application.purchase.retrive;

public record RetrivePurchaseCommand(
        String id,
        String currency
) {
    public static RetrivePurchaseCommand with(
            final String id,
            final String currency
    ) {
        return new RetrivePurchaseCommand(id, currency);
    }
}
