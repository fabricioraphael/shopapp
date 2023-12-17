package com.frb.infrastructure.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.frb.application.purchase.create.CreatePurchaseOutput;
import com.frb.application.purchase.create.CreatePurchaseUseCase;
import com.frb.application.purchase.retrive.GetPurchaseByIdUseCase;
import com.frb.application.purchase.retrive.PurchaseOutput;
import com.frb.application.purchase.retrive.RetrivePurchaseCommand;
import com.frb.domain.exceptions.DomainException;
import com.frb.domain.exceptions.NotFoundException;
import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseAmount;
import com.frb.domain.purchase.PurchaseID;
import com.frb.domain.validation.Error;
import com.frb.domain.validation.handler.Notification;
import com.frb.infrastructure.ControllerTest;
import com.frb.infrastructure.purchase.models.PurchaseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = PurchaseAPI.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreatePurchaseUseCase createPurchaseUseCase;

    @MockBean
    private GetPurchaseByIdUseCase getPurchaseByIdUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreatePurchase_shouldReturnPurchaseId() throws Exception {
        // given
        final var expectedDescription = "Purchase Description";
        final var expectedPurchaseDate = LocalDate.now();
        final var expectedAmount = BigDecimal.valueOf(11.47);

        final var purchaseOutput = CreatePurchaseOutput.from("123", expectedDescription, expectedPurchaseDate, expectedAmount.doubleValue());

        final var aInput =
                new PurchaseRequest(expectedDescription, expectedPurchaseDate, expectedAmount);

        when(createPurchaseUseCase.execute(any()))
                .thenReturn(Right(purchaseOutput));

        // when
        final var request = post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/purchases/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createPurchaseUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedPurchaseDate, cmd.purchaseDate())
                        && Objects.equals(expectedAmount, cmd.amount())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreatePurchase_thenShouldReturnNotification() throws Exception {
        // given
        final var expectedDescription = "Purchase Description & Purchase Description & Purchase Description";
        final var expectedPurchaseDate = LocalDate.now();
        final var expectedAmount = BigDecimal.valueOf(11.47);
        final var expectedMessage = "'description' must be less than 50 characters";

        final var aInput =
                new PurchaseRequest(expectedDescription, expectedPurchaseDate, expectedAmount);

        when(createPurchaseUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        // when
        final var request = post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createPurchaseUseCase, times(1)).execute(argThat(cmd ->
                        Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedPurchaseDate, cmd.purchaseDate())
                        && Objects.equals(expectedAmount, cmd.amount())
        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreatePurchase_thenShouldReturnDomainException() throws Exception {
        // given
        final var expectedDescription = "Purchase Description & Purchase Description & Purchase Description";
        final var expectedPurchaseDate = LocalDate.now();
        final var expectedAmount = BigDecimal.valueOf(11.47);
        final var expectedMessage = "'description' must be less than 50 characters";

        final var aInput =
                new PurchaseRequest(expectedDescription, expectedPurchaseDate, expectedAmount);

        when(createPurchaseUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        // when
        final var request = post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createPurchaseUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedPurchaseDate, cmd.purchaseDate())
                        && Objects.equals(expectedAmount, cmd.amount())
        ));
    }

    @Test
    public void givenAValidId_whenCallsGetPurchase_shouldReturnPurchase() throws Exception {
        // given
        final var expectedDescription = "Purchase Description";
        final var expectedPurchaseDate = LocalDate.now();
        final var expectedAmount = PurchaseAmount.from(BigDecimal.valueOf(10.0));

        final var aPurchase =
                Purchase.newPurchase(expectedDescription, expectedPurchaseDate, expectedAmount);

        final var expectedId = aPurchase.getId().getValue();

        final var aCommand = RetrivePurchaseCommand.with(expectedId, "Real");

        when(getPurchaseByIdUseCase.execute(any()))
                .thenReturn(PurchaseOutput.from(aPurchase));

        // when
        final var request = get("/purchases/{id}?currencyConversion=Real", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(jsonPath("$.originalAmount", equalTo(expectedAmount.getValue().doubleValue())));

        verify(getPurchaseByIdUseCase, times(1)).execute(eq(aCommand));
    }

    @Test
    public void givenAInvalidId_whenCallsGetPurchases_shouldReturnNotFound() throws Exception {
        // given
        final var expectedErrorMessage = "Purchase with ID 123 was not found";
        final var expectedId = PurchaseID.from("123");

        when(getPurchaseByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Purchase.class, expectedId));

        // when
        final var request = get("/purchases/{id}?currencyConversion=Real", expectedId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }
}
