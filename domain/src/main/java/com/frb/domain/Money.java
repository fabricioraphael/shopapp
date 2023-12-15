package com.frb.domain;

public abstract class Money extends ValueObject {

    public abstract Double getValue();

    public abstract Double getValueRounded();

    public abstract Double calcWithRateRounded(Double rate);
}
