package org.xedlab.commonDomain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Maney {

    private final BigDecimal amount;

    public static final Maney ZERO = new Maney(BigDecimal.ZERO);

    public Maney(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThenZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThen(Maney maney) {
        return this.amount != null && this.amount.compareTo(maney.getAmount()) > 0;
    }

    public Maney add(Maney maney) {
        return new Maney(setScale(this.amount.add(maney.getAmount())));
    }

    public Maney subtract(Maney maney) {
        return new Maney(setScale(this.amount.subtract(maney.getAmount())));
    }

    public Maney multiply(int multiplier) {
        return new Maney(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maney maney = (Maney) o;
        return Objects.equals(amount, maney.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
