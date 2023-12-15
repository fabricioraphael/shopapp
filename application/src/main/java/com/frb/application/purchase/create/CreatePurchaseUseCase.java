package com.frb.application.purchase.create;

import com.frb.domain.validation.handler.Notification;
import com.frb.application.UseCase;
import io.vavr.control.Either;

public abstract class CreatePurchaseUseCase
        extends UseCase<CreatePurchaseCommand, Either<Notification, CreatePurchaseOutput>> {
}
