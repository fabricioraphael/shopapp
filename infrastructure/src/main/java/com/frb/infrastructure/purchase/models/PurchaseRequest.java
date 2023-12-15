package com.frb.infrastructure.purchase.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PurchaseRequest(
        @JsonProperty("description") String description,
        @DateTimeFormat( pattern = "yyyy-MM-dd")
        @JsonProperty("purchaseDate") LocalDate purchaseDate,
        @JsonProperty("amount") Double amount
) { }
