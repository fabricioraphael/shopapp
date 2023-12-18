package com.frb.application.purchase.create;

import com.frb.application.UseCase;
import com.frb.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreatePurchaseUseCase
        extends UseCase<CreatePurchaseCommand, Either<Notification, CreatePurchaseOutput>> {
}
