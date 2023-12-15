package com.frb.infrastructure.configuration.usecases;

import com.frb.application.purchase.create.CreatePurchaseUseCase;
import com.frb.application.purchase.create.CreatePurchaseUseCaseImpl;
import com.frb.application.purchase.retrive.GetPurchaseByIdUseCase;
import com.frb.application.purchase.retrive.GetPurchaseByIdUseCaseImpl;
import com.frb.domain.fiscalRecord.FiscalRecordGateway;
import com.frb.domain.purchase.PurchaseGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchaseUseCaseConfig {
    private final PurchaseGateway purchaseGateway;
    private final FiscalRecordGateway fiscalRecordGateway;

    public PurchaseUseCaseConfig(final PurchaseGateway purchaseGateway, final FiscalRecordGateway fiscalRecordGateway) {
        this.purchaseGateway = purchaseGateway;
        this.fiscalRecordGateway = fiscalRecordGateway;
    }

    @Bean
    public CreatePurchaseUseCase createPurchaseUseCase() {
        return new CreatePurchaseUseCaseImpl(purchaseGateway);
    }

    @Bean
    public GetPurchaseByIdUseCase getPurchaseByIdUseCase() {
        return new GetPurchaseByIdUseCaseImpl(purchaseGateway, fiscalRecordGateway);
    }
}
