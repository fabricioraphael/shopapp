package com.frb.domain.purchase;

import com.frb.domain.AggregateRoot;
import com.frb.domain.validation.ValidationHandler;

import java.time.LocalDate;
import java.util.Objects;

public class Purchase extends AggregateRoot<PurchaseID> implements Cloneable {
    private String description;
    private LocalDate purchaseDate;
    private PurchaseAmount amount;

    private Purchase(
            final PurchaseID anId,
            final String aDescription,
            final LocalDate aPurchaseDate,
            final PurchaseAmount anAmount
    ) {
        super(anId);
        this.description = aDescription;
        this.purchaseDate = Objects.requireNonNull(aPurchaseDate, "'purchaseDate' should not be null");
//        this.purchaseDate = aPurchaseDate;
        this.amount = anAmount;
    }

    public static Purchase newPurchase(
            final String aDescription,
            final LocalDate aPurchaseDate,
            final PurchaseAmount anAmount
    ) {
        final var id = PurchaseID.unique();
        return new Purchase(id, aDescription, aPurchaseDate, anAmount);
    }

    public static Purchase with(
            final PurchaseID anId,
            final String description,
            final LocalDate purchaseDate,
            final PurchaseAmount amount
    ) {
        return new Purchase(
                anId,
                description,
                purchaseDate,
                amount
        );
    }

    public static Purchase with(final Purchase aPurchase) {
        return with(
                aPurchase.getId(),
                aPurchase.description,
                aPurchase.purchaseDate,
                aPurchase.amount
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new PurchaseValidator(this, handler).validate();
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public PurchaseAmount getAmount() {
        return amount;
    }

    @Override
    public Purchase clone() {
        try {
            return (Purchase) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "description='" + description + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", amount=" + amount +
                '}';
    }
}
