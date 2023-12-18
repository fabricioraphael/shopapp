package com.frb.domain.exceptions;

import com.frb.domain.validation.Error;

import java.util.List;

public abstract class ExceptionWithErrors extends NoStacktraceException {
    protected final List<Error> errors;
    public ExceptionWithErrors(String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public ExceptionWithErrors(String message, Throwable cause, List<Error> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
