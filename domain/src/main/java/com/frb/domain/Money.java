package com.frb.domain;

import java.math.BigDecimal;

public abstract class Money extends ValueObject {

    public abstract BigDecimal getValue();

    public abstract Double getValueRounded();

    public abstract Double calcWithRateRounded(Double rate);
}
