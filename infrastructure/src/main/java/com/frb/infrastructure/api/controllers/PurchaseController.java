package com.frb.infrastructure.api.controllers;

import com.frb.application.purchase.create.CreatePurchaseCommand;
import com.frb.application.purchase.create.CreatePurchaseOutput;
import com.frb.application.purchase.create.CreatePurchaseUseCase;
import com.frb.application.purchase.retrive.GetPurchaseByIdUseCase;
import com.frb.application.purchase.retrive.RetrivePurchaseCommand;
import com.frb.domain.validation.handler.Notification;
import com.frb.infrastructure.api.PurchaseAPI;
import com.frb.infrastructure.purchase.models.PurchaseRequest;
import com.frb.infrastructure.purchase.models.PurchaseResponse;
import com.frb.infrastructure.purchase.presenters.PurchaseApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class PurchaseController implements PurchaseAPI {

    private final CreatePurchaseUseCase createPurchaseUseCase;
    private final GetPurchaseByIdUseCase getPurchaseByIdUseCase;
    public PurchaseController(final CreatePurchaseUseCase createPurchaseUseCase, final GetPurchaseByIdUseCase getPurchaseByIdUseCase) {
        this.createPurchaseUseCase = Objects.requireNonNull(createPurchaseUseCase);
        this.getPurchaseByIdUseCase = Objects.requireNonNull(getPurchaseByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createPurchase(final PurchaseRequest input) {
        final var aCommand = CreatePurchaseCommand.with(
                input.description(),
                input.purchaseDate(),
                input.amount()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreatePurchaseOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/purchases/" + output.id())).body(output);

        return this.createPurchaseUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public PurchaseResponse getById(final String id, String currencyConversion) {
        var aCommand = RetrivePurchaseCommand.with(id, currencyConversion);
        return PurchaseApiPresenter.present(this.getPurchaseByIdUseCase.execute(aCommand));
    }
}
