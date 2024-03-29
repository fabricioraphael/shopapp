package com.frb.infrastructure.treasuryGov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frb.domain.exceptions.FiscalDataErrorException;
import com.frb.domain.fiscalRecord.FiscalRecord;
import com.frb.domain.fiscalRecord.FiscalRecordGateway;
import com.frb.infrastructure.api.controllers.GlobalExceptionHandler;
import com.frb.infrastructure.configuration.Properties;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class TreasuryFiscalDataGatewayImpl implements FiscalRecordGateway {

    private static final Logger log = LoggerFactory.getLogger(TreasuryFiscalDataGatewayImpl.class);
    private final static String ratesOfExchangeEndpoint = "/api/fiscal_service/v1/accounting/od/rates_of_exchange";
    private final ObjectMapper mapper;
    private final Properties properties;



    public TreasuryFiscalDataGatewayImpl(final ObjectMapper mapper, final Properties properties) {
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    @CircuitBreaker(name = "treasuryFiscalDataRates", fallbackMethod = "fallbackTreasuryFiscalDataRates")
    public Optional<FiscalRecord.FRecord> findRatesOfExchangeByDate(final LocalDate dateQuery, final String currencyQuery) {
        final String uri = "%s%s?filter=record_date:lte:%s,currency:eq:%s"
                .formatted(properties.getShopapp().getTreasury().getBaseUrl(), ratesOfExchangeEndpoint, dateQuery, currencyQuery);

        log.info("Get request url:" + uri);

        try {
            var response = get(uri);
            if (response.statusCode() == 200){
                TreasuryFiscalDataResponse treasuryFiscalDataResponse = mapper.readValue(response.body(), TreasuryFiscalDataResponse.class);

                List<FiscalRecord.FRecord> records = treasuryFiscalDataResponse.records().stream().map(rec -> FiscalRecord.FRecord.with(
                        rec.recordDate(),
                        rec.country(),
                        rec.country(),
                        rec.exchangeRate(),
                        rec.effectiveDate()
                )).toList();

                return new FiscalRecord(records).filterRecordsUpTo6MonthsAgo(dateQuery);
            } else {
                throw FiscalDataErrorException.create();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<FiscalRecord.FRecord> fallbackTreasuryFiscalDataRates(final LocalDate dateQuery, final String currencyQuery, final Throwable throwable) {
        log.warn("[fallbackTreasuryFiscalDataRates] The circuit is OPEN");
        throw FiscalDataErrorException.with("third partner gateway error. circuit is open.");
    }

    private HttpResponse<String> get(final String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(5))
                .build();

        var httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}
