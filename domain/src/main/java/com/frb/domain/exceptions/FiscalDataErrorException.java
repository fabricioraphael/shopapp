package com.frb.domain.exceptions;

public class FiscalDataErrorException extends NoStacktraceException {
    private static final String defaultMessage = "error in the third partner gateway.";

    protected FiscalDataErrorException(final String aMessage) {
        super(aMessage);
    }

    public static FiscalDataErrorException with(String message) {
        return new FiscalDataErrorException(message);
    }

    public static FiscalDataErrorException create() {
        return new FiscalDataErrorException(defaultMessage);
    }
}
