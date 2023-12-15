package com.frb.domain.purchase;

import com.frb.domain.Identifier;
import com.frb.domain.utils.IdUtils;

import java.util.Objects;

public class PurchaseID extends Identifier {
    private final String value;

    private PurchaseID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static PurchaseID unique() {
        return PurchaseID.from(IdUtils.uuid());
    }

    public static PurchaseID from(final String anId) {
        return new PurchaseID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PurchaseID that = (PurchaseID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
