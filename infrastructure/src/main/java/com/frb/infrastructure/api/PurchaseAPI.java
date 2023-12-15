package com.frb.infrastructure.api;

import com.frb.infrastructure.purchase.models.PurchaseRequest;
import com.frb.infrastructure.purchase.models.PurchaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "purchases")
public interface PurchaseAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new purchase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createPurchase(@RequestBody PurchaseRequest input);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a purchase by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Purchase was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    PurchaseResponse getById(@PathVariable(name = "id") String id, @RequestParam String currencyConversion);
}
