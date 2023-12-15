package com.frb.domain.purchase;

import java.util.Optional;

public interface PurchaseGateway {

    Purchase create(Purchase aPurchase);

    Optional<Purchase> findById(PurchaseID anId);
}
