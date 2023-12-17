package com.frb.application.purchase;

import com.frb.application.UseCaseTest;
import com.frb.application.purchase.create.CreatePurchaseCommand;
import com.frb.application.purchase.create.CreatePurchaseUseCaseImpl;
import com.frb.domain.purchase.PurchaseGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CreatePurchaseUseCaseTest  extends UseCaseTest {

    @InjectMocks
    private CreatePurchaseUseCaseImpl useCase;

    @Mock
    private PurchaseGateway purchaseGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(purchaseGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreatePurchase_shouldReturnPurchaseId() {
        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = BigDecimal.valueOf(11.47);

        final var aCommand =
                CreatePurchaseCommand.with(expectedDescription, purchaseDate, amount);

        when(purchaseGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(purchaseGateway, times(1)).create(argThat(aPurchase ->
                        Objects.equals(expectedDescription, aPurchase.getDescription())
                        && Objects.equals(purchaseDate, aPurchase.getPurchaseDate())
                        && Objects.equals(amount, aPurchase.getAmount().getValue())
                        && Objects.nonNull(aPurchase.getId())
        ));
    }

    @Test
    public void givenAInvalidDescription_whenCallsCreatePurchase_thenShouldReturnDomainException() {
        final var expectedDescription = """
                description more than 50 caracteres & description more than 50 caracteres
                """;
        final var expectedErrorMessage = "'description' must be less than 50 characters";
        final var expectedErrorCount = 1;
        final LocalDate purchaseDate = LocalDate.now();
        final var amount = BigDecimal.valueOf(11.36);

        final var aCommand =
                CreatePurchaseCommand.with(expectedDescription, purchaseDate, amount);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(purchaseGateway, times(0)).create(any());
    }
}