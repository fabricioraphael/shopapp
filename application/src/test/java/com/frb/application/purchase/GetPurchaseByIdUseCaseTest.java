package com.frb.application.purchase;

import com.frb.application.UseCaseTest;
import com.frb.application.purchase.retrive.GetPurchaseByIdUseCaseImpl;
import com.frb.application.purchase.retrive.RetrivePurchaseCommand;
import com.frb.domain.exceptions.NotFoundException;
import com.frb.domain.fiscalRecord.FiscalRecord;
import com.frb.domain.fiscalRecord.FiscalRecordGateway;
import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseAmount;
import com.frb.domain.purchase.PurchaseGateway;
import com.frb.domain.purchase.PurchaseID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GetPurchaseByIdUseCaseTest  extends UseCaseTest {

    @InjectMocks
    private GetPurchaseByIdUseCaseImpl useCase;

    @Mock
    private PurchaseGateway purchaseGateway;

    @Mock
    private FiscalRecordGateway fiscalRecordGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(purchaseGateway, fiscalRecordGateway);
    }

    @Test
    public void givenAValidPurchaseId_whenCallsGetPurchase_shouldReturnPurchase() {
        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(10.0));
        final var currency = "Real";
        final var exchangeRate = Double.valueOf(1.5);
        final var exchangeConversionAmount = Double.valueOf(15.0);
        final var recordDate = purchaseDate.minusMonths(3);

        final var aPurchase = Purchase.newPurchase(expectedDescription, purchaseDate, amount);
        final var expectedId = aPurchase.getId();
        final var aCommand = RetrivePurchaseCommand.with(expectedId.getValue(), currency);
        final var rate = FiscalRecord.FRecord.with(recordDate, "Brazil", currency, exchangeRate.toString(), recordDate);

        when(purchaseGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aPurchase.clone()));

        when(fiscalRecordGateway.findRatesOfExchangeByDate(purchaseDate, aCommand.currency()))
                .thenReturn(Optional.of(rate));

        final var actualPurchase = useCase.execute(aCommand);

        Assertions.assertEquals(expectedId, actualPurchase.id());
        Assertions.assertEquals(expectedDescription, actualPurchase.description());
        Assertions.assertEquals(purchaseDate, actualPurchase.purchaseDate());
        Assertions.assertEquals(amount.getValue().doubleValue(), actualPurchase.originalAmount());
        Assertions.assertEquals(Double.valueOf(rate.exchangeRate()), actualPurchase.exchangeRate());
        Assertions.assertEquals(exchangeConversionAmount, actualPurchase.convertedAmount());
    }

    @Test
    public void givenAInvalidId_whenCallsGetPurchase_shouldReturnNotFound() {
        final var expectedErrorMessage = "Purchase with ID 123 was not found";
        final var expectedId = PurchaseID.from("123");

        final var aCommand = RetrivePurchaseCommand.with(expectedId.getValue(), "Real");

        when(purchaseGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(aCommand)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = PurchaseID.from("123");

        final var aCommand = RetrivePurchaseCommand.with(expectedId.getValue(), "Real");

        when(purchaseGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(aCommand)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
