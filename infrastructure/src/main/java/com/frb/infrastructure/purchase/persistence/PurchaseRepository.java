package com.frb.infrastructure.purchase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, String> {
}
