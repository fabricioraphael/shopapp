package com.frb.domain.purchase;

import com.frb.domain.validation.Error;
import com.frb.domain.validation.ValidationHandler;
import com.frb.domain.validation.Validator;

public class PurchaseValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 50;
    private final Purchase purchase;

    public PurchaseValidator(final Purchase aPurchase, final ValidationHandler aHandler) {
        super(aHandler);
        this.purchase = aPurchase;
    }

    @Override
    public void validate() {
        checkDescriptionConstraints();
        checkAmountConstraints();
    }

    private void checkDescriptionConstraints() {
        final var description = this.purchase.getDescription();
        final int length = description.trim().length();
        if (length > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'description' must be less than 50 characters"));
        }
    }

    private void checkAmountConstraints() {
        final var amount = this.purchase.getAmount();
        if (amount.getValue() < 0.0) {
            this.validationHandler().append(new Error("'amount' must be a valid positive amount"));
        }
    }
}