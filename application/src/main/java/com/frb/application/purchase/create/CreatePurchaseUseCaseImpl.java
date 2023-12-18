package com.frb.application.purchase.create;

import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseAmount;
import com.frb.domain.purchase.PurchaseGateway;
import com.frb.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class CreatePurchaseUseCaseImpl extends CreatePurchaseUseCase {

    private final PurchaseGateway purchaseGateway;

    public CreatePurchaseUseCaseImpl(final PurchaseGateway purchaseGateway) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
    }

    @Override
    public Either<Notification, CreatePurchaseOutput> execute(final CreatePurchaseCommand aCommand) {
        final var aDescription = aCommand.description();
        final var aPurchaseDate = aCommand.purchaseDate();

        final var anAmount = PurchaseAmount.from(aCommand.amount());

        final var notification = Notification.create();

        final var aPurchase = Purchase.newPurchase(aDescription, aPurchaseDate, anAmount);

        aPurchase.validate(notification);

        return notification.hasError() ? Left(notification) : create(aPurchase);
    }

    private Either<Notification, CreatePurchaseOutput> create(final Purchase aPurchase) {
        return Try(() -> this.purchaseGateway.create(aPurchase))
                .toEither()
                .bimap(Notification::create, CreatePurchaseOutput::from);
    }
}