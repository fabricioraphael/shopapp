package com.frb.infrastructure.purchase.persistence;


import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseAmount;
import com.frb.domain.purchase.PurchaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Purchase")
@Table(name = "purchases")
public class PurchaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "purchaseDate")
    private LocalDate purchaseDate;

    @Column(name = "amount")
    private BigDecimal amount;

    public PurchaseEntity() {
    }

    private PurchaseEntity(
            final String id,
            final String description,
            final LocalDate purchaseDate,
            final BigDecimal amount
    ) {
        this.id = id;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
    }

    public static PurchaseEntity from(final Purchase aPurchase) {
        return new PurchaseEntity(
                aPurchase.getId().getValue(),
                aPurchase.getDescription(),
                aPurchase.getPurchaseDate(),
                aPurchase.getAmount().getValue()
        );
    }

    public Purchase toAggregate() {
        return Purchase.with(
                PurchaseID.from(getId()),
                getDescription(),
                getPurchaseDate(),
                PurchaseAmount.from(getAmount())
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
