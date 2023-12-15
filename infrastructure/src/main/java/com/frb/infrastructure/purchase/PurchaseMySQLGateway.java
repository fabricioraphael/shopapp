package com.frb.infrastructure.purchase;

import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseGateway;
import com.frb.domain.purchase.PurchaseID;
import com.frb.infrastructure.purchase.persistence.PurchaseEntity;
import com.frb.infrastructure.purchase.persistence.PurchaseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PurchaseMySQLGateway implements PurchaseGateway {

    private final PurchaseRepository repository;

    public PurchaseMySQLGateway(final PurchaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Purchase create(Purchase aPurchase) {
        return this.repository.save(PurchaseEntity.from(aPurchase)).toAggregate();
    }

    @Override
    public Optional<Purchase> findById(PurchaseID anId) {
        return this.repository.findById(anId.getValue())
                .map(PurchaseEntity::toAggregate);
    }
}
