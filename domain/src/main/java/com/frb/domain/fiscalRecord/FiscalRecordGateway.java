package com.frb.domain.fiscalRecord;

import java.time.LocalDate;
import java.util.Optional;

public interface FiscalRecordGateway {

    Optional<FiscalRecord.FRecord> findRatesOfExchangeByDate(final LocalDate dateQuery, final String currencyQuery);
}
