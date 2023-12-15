package com.frb.domain.exceptions;

public class PurchaseConversionException extends NoStacktraceException {
    private static final String defaultMessage = "the purchase cannot be converted to the target currency";

    protected PurchaseConversionException(final String aMessage) {
        super(aMessage);
    }

    public static PurchaseConversionException with(String message) {
        return new PurchaseConversionException(message);
    }

    public static PurchaseConversionException create() {
        return new PurchaseConversionException(defaultMessage);
    }
}
