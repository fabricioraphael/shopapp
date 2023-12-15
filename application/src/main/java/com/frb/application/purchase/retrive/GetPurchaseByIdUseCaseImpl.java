package com.frb.application.purchase.retrive;

import com.frb.domain.exceptions.NotFoundException;
import com.frb.domain.exceptions.PurchaseConversionException;
import com.frb.domain.fiscalRecord.FiscalRecordGateway;
import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseGateway;
import com.frb.domain.purchase.PurchaseID;

import java.util.Objects;
import java.util.function.Supplier;

public class GetPurchaseByIdUseCaseImpl extends GetPurchaseByIdUseCase {

    private final PurchaseGateway purchaseGateway;

    private final FiscalRecordGateway fiscalRecordGateway;

    public GetPurchaseByIdUseCaseImpl(final PurchaseGateway purchaseGateway, final FiscalRecordGateway fiscalRecordGateway) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
        this.fiscalRecordGateway = Objects.requireNonNull(fiscalRecordGateway);
    }

    @Override
    public PurchaseOutput execute(RetrivePurchaseCommand aCommand) {
        final var aPruchaseID = PurchaseID.from(aCommand.id());

        var retrivedPurchase = this.purchaseGateway.findById(aPruchaseID)
                .orElseThrow(notFound(aPruchaseID));

        if (aCommand.currency() != null) {
            var tax = fiscalRecordGateway.findRatesOfExchangeByDate(retrivedPurchase.getPurchaseDate(), aCommand.currency());

            if (tax.isPresent()) {
                var exchangeRate = Double.valueOf(tax.get().exchangeRate());

                var convertedAmount = retrivedPurchase.getAmount().calcWithRateRounded(exchangeRate);

                return PurchaseOutput.withRate(retrivedPurchase, exchangeRate, convertedAmount);
            } else {
                throw PurchaseConversionException.create();
            }
        }

        return PurchaseOutput.from(retrivedPurchase);
    }

    private Supplier<NotFoundException> notFound(final PurchaseID anId) {
        return () -> NotFoundException.with(Purchase.class, anId);
    }
}
