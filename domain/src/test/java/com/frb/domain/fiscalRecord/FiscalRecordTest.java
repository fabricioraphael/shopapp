package com.frb.domain.fiscalRecord;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class FiscalRecordTest {

    @Test
    public void givenAValidParams_whenCallNewFiscalRecord_thenInstantiateAFiscalRecord() {
        final LocalDate recordDate = LocalDate.now();
        final String country = "Brazil";
        final String currency = "Real";
        final String exchangeRate = "5.033";
        final LocalDate effectiveDat = recordDate;

        var records = List.of(
                FiscalRecord.FRecord.with(recordDate, country, currency, exchangeRate, effectiveDat)
        );

        var fiscalRecord = new FiscalRecord(records);

        Assertions.assertNotNull(fiscalRecord);
        Assertions.assertNotNull(fiscalRecord.records());
        Assertions.assertEquals(1, fiscalRecord.records().size());
        Assertions.assertEquals(fiscalRecord.records().get(0).recordDate(), recordDate);
    }

    @Test
    public void givenAValidParams_whenCallFilterRecords_thenReturnARightRecord() {
        final LocalDate recordDate = LocalDate.now();
        final String country = "Brazil";
        final String currency = "Real";
        final String exchangeRate = "1.5";
        final LocalDate effectiveDat = recordDate;

        var records = List.of(
                FiscalRecord.FRecord.with(recordDate.minusMonths(6), country, currency, "3", effectiveDat.minusMonths(6)),
                FiscalRecord.FRecord.with(recordDate.minusMonths(3), country, currency, "2", effectiveDat.minusMonths(3)),
                FiscalRecord.FRecord.with(recordDate, country, currency, exchangeRate, effectiveDat),
                FiscalRecord.FRecord.with(recordDate.minusMonths(9), country, currency, "4", effectiveDat.minusMonths(9)),
                FiscalRecord.FRecord.with(recordDate.minusMonths(12), country, currency, "4", effectiveDat.minusMonths(12))
        );

        var recordFiltered = new FiscalRecord(records).filterRecordsUpTo6MonthsAgo(recordDate);

        Assertions.assertNotNull(recordFiltered);
        Assertions.assertEquals(exchangeRate, recordFiltered.get().exchangeRate());
    }

    @Test
    public void givenValidDate_whenThereIsNoReportForTheLast6Months_thenReturnNoRecord() {
        final LocalDate recordDate = LocalDate.now();
        final String country = "Brazil";
        final String currency = "Real";
        final String exchangeRate = "1.5";
        final LocalDate effectiveDat = recordDate;

        var records = List.of(
                FiscalRecord.FRecord.with(recordDate.minusMonths(7), country, currency, exchangeRate, effectiveDat.minusMonths(6)),
                FiscalRecord.FRecord.with(recordDate.minusMonths(9), country, currency, exchangeRate, effectiveDat.minusMonths(9)),
                FiscalRecord.FRecord.with(recordDate.minusMonths(12), country, currency, exchangeRate, effectiveDat.minusMonths(12))
        );

        var recordFiltered = new FiscalRecord(records).filterRecordsUpTo6MonthsAgo(recordDate);

        Assertions.assertNotNull(recordFiltered);
        Assertions.assertFalse(recordFiltered.isPresent());
    }
}
