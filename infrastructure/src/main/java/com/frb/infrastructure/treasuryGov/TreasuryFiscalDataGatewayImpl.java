package com.frb.infrastructure.treasuryGov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frb.domain.fiscalRecord.FiscalRecord;
import com.frb.domain.fiscalRecord.FiscalRecordGateway;
import com.frb.infrastructure.configuration.Properties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@Component
public class TreasuryFiscalDataGatewayImpl implements FiscalRecordGateway {
    private final static String ratesOfExchangeEndpoint = "/api/fiscal_service/v1/accounting/od/rates_of_exchange";
    private final ObjectMapper mapper;
    private final Properties properties;

    public TreasuryFiscalDataGatewayImpl(final ObjectMapper mapper, final Properties properties) {
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    public Optional<FiscalRecord.FRecord> findRatesOfExchangeByDate(final LocalDate dateQuery, final String currencyQuery) {
        final String uri = "%s%s?filter=record_date:lte:%s,currency:eq:%s"
                .formatted(properties.getShopapp().getTreasury().getBaseUrl(), ratesOfExchangeEndpoint, dateQuery, currencyQuery);

        System.out.println(">>>>>>>>>>> properties.url " + uri);
        try {
            var response = get(uri);
            TreasuryFiscalDataResponse treasuryFiscalDataResponse = mapper.readValue(response, TreasuryFiscalDataResponse.class);

            var date6MonthsAgo = dateQuery.minusMonths(6);

            System.out.println(">>>>>>>> treasuryResponse: " + treasuryFiscalDataResponse);

            var recordFiltered = treasuryFiscalDataResponse.records().stream()
                    .filter(rec -> rec.effectiveDate().isAfter(date6MonthsAgo) && rec.effectiveDate().isBefore(dateQuery))
                    .sorted(Comparator.comparing(TreasuryFiscalDataResponse.TRecord::recordDate).reversed())
                    .findFirst();

            System.out.println(">>>>>>>> recordsRates: " + recordFiltered);

            return recordFiltered.map(rec -> FiscalRecord.FRecord.with(
                    rec.recordDate(),
                    rec.country(),
                    rec.currency(),
                    rec.exchangeRate(),
                    rec.effectiveDate()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String get(final String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(3))
                .build();

        var httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
