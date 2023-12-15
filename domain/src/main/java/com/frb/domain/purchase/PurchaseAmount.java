package com.frb.domain.purchase;

import com.frb.domain.Money;

import java.util.Objects;

public class PurchaseAmount extends Money {
    private final Double value;
    private static final int places = 2;
    private static final int roundBase = 10;

    private PurchaseAmount(final Double value) {
        this.value = Objects.requireNonNull(value);
    }

    public static PurchaseAmount from(final Double anAmount) {
        return new PurchaseAmount(anAmount);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public Double getValueRounded() {
        return round(this.value);
    }

    @Override
    public Double calcWithRateRounded(Double rate) {
        return round(this.value*rate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PurchaseAmount that = (PurchaseAmount) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    private static Double round(Double value) {
        long factor = (long) Math.pow(roundBase, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
