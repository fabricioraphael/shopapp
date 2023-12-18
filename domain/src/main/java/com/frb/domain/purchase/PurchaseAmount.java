package com.frb.domain.purchase;

import com.frb.domain.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PurchaseAmount extends Money {
    private final BigDecimal value;
    private static final int roundScale = 2;

    private PurchaseAmount(final BigDecimal value) {
        this.value = Objects.requireNonNull(value, "'amount' should not be null");
    }

    public static PurchaseAmount from(final BigDecimal anAmount) {
        return new PurchaseAmount(anAmount);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Double getValueRounded() {
        return round(this.value);
    }

    @Override
    public Double calcWithRateRounded(Double rate) {
        return round(this.value.multiply(new BigDecimal(rate)));
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

    private static Double round(final BigDecimal value) {
        return value.setScale(roundScale, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        return "PurchaseAmount{" +
                "value=" + value +
                '}';
    }
}
