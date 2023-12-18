package com.frb.domain.fiscalRecord;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record FiscalRecord(
        List<FRecord> records
) {

    private static final int lessThanMonths = 6;
    public Optional<FRecord> filterRecordsUpTo6MonthsAgo(LocalDate dateQuery) {
        var date6MonthsAgo = dateQuery.minusMonths(lessThanMonths);

        return this.records.stream()
                .filter(rec -> rec.effectiveDate().isAfter(date6MonthsAgo) && !rec.effectiveDate().isAfter(dateQuery))
                .sorted(Comparator.comparing(FiscalRecord.FRecord::recordDate).reversed())
                .findFirst();
    }

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