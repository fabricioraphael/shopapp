package com.frb.domain.fiscalRecord;

import java.time.LocalDate;
import java.util.List;

public record FiscalRecord(
        List<FRecord> records
) {
    public record FRecord(
            LocalDate recordDate,
            String country,
            String currency,
            String exchangeRate,
            LocalDate effectiveDate
    ) {
        public static FRecord with(final LocalDate recordDate,
                                   final String country,
                                   final String currency,
                                   final String exchangeRate,
                                   final LocalDate effectiveDate) {
            return new FRecord(
                        recordDate,
                        country,
                        currency,
                        exchangeRate,
                        effectiveDate);
        }

        @Override
        public String toString() {
            return "FRecord{" +
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
        return "FiscalRecord{" +
                "records=" + records +
                '}';
    }
}