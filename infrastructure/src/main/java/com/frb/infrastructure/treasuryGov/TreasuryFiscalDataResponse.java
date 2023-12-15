package com.frb.infrastructure.treasuryGov;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record TreasuryFiscalDataResponse(
        @JsonProperty("data")
        List<TRecord> records
) {
    public record TRecord(
            @JsonProperty("record_date")
            @DateTimeFormat( pattern = "yyyy-MM-dd")
            LocalDate recordDate,
            @JsonProperty("country")
            String country,
            @JsonProperty("currency")
            String currency,
            @JsonProperty("exchange_rate")
            String exchangeRate,
            @JsonProperty("effective_date")
            @DateTimeFormat( pattern = "yyyy-MM-dd")
            LocalDate effectiveDate
    ) {

        @Override
        public String toString() {
            return "TRecord{" +
                    "recordDate=" + recordDate +
                    ", country='" + country + '\'' +
                    ", currency='" + currency + '\'' +
                    ", exchangeRate='" + exchangeRate + '\'' +
                    ", effectiveDate='" + effectiveDate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TreasuryFiscalDataResponse{" +
                "records=" + records +
                '}';
    }
}
