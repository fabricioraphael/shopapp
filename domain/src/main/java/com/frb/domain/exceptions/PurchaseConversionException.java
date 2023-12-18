package com.frb.domain.exceptions;

import com.frb.domain.validation.Error;

import java.util.List;

public class PurchaseConversionException extends ExceptionWithErrors {
    private static final String defaultMessage = "the purchase cannot be converted to the target currency";

    protected PurchaseConversionException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static PurchaseConversionException with(String message) {
        return new PurchaseConversionException(message, List.of());
    }

    public static PurchaseConversionException createDefault() {
        var error = new Error("We were unable to retrieve a tax report for that date and currency.");
        return new PurchaseConversionException(defaultMessage, List.of(error));
    }
}
