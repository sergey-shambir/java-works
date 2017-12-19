package name.sergey.shambir.quantity;

import name.sergey.shambir.utils.DecimalUtils;

import java.math.BigDecimal;

public class Quantity implements Comparable<Quantity> {
    private final QuantityCategory category;
    private final BigDecimal value;

    public static Quantity countable(int value) {
        return new Quantity(new BigDecimal(value), QuantityCategory.Countable);
    }

    public static Quantity uncountable(double value) {
        return new Quantity(new BigDecimal(value), QuantityCategory.Uncountable);
    }

    public Quantity(QuantityCategory category) {
        this.category = category;
        this.value = normalize(BigDecimal.ZERO, category);
    }

    public Quantity(BigDecimal value, QuantityCategory category) {
        this.category = category;
        this.value = normalize(value, category);
    }

    public final BigDecimal value() {
        return this.value;
    }

    public final QuantityCategory getCategory() {
        return this.category;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }
        Quantity otherQuantity = (Quantity)other;
        return (this.category.equals(otherQuantity.category))
                && (this.value.equals(otherQuantity.value));
    }

    @Override
    public int compareTo(Quantity other) {
        if (this.category != other.category) {
            throw new RuntimeException("cannot compare quantities of different category");
        }
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        String measureUnits;
        switch (this.category) {
            case Countable:
                measureUnits = (this.value.intValue() == 1) ? "unit" : "units";
                break;
            case Uncountable:
                measureUnits = "kg";
                break;
            default:
                throw new RuntimeException("unknown quantity category");
        }
        return this.value.toString() + " " + measureUnits;
    }

    public final boolean isZero() {
        switch (this.category) {
            case Countable:
                return this.value.compareTo(BigDecimal.ZERO) == 0;
            case Uncountable:
                return this.value.compareTo(DecimalUtils.ZERO_AMOUNT) == 0;
            default:
                throw new RuntimeException("unknown quantity category");
        }
    }

    public final Quantity add(Quantity other) {
        if (other.isZero()) {
            return new Quantity(this.value, this.category);
        }
        if (this.isZero()) {
            return new Quantity(other.value, other.category);
        }
        if (this.category != other.category) {
            throw new RuntimeException("cannot add quantity of different category");
        }
        return new Quantity(this.value.add(other.value), this.category);
    }

    public final Quantity subtract(Quantity other) {
        if (other.isZero()) {
            return new Quantity(this.value, this.category);
        }
        if (this.category != other.category) {
            throw new RuntimeException("cannot subtract quantity of different category");
        }
        BigDecimal value = this.value.subtract(other.value);
        if (value.compareTo(normalize(BigDecimal.ZERO, this.category)) < 0) {
            throw new RuntimeException("cannot subtract more quantity than available");
        }
        return new Quantity(value, this.category);
    }

    private static BigDecimal normalize(BigDecimal value, QuantityCategory category) {
        switch (category) {
            case Countable:
                value = DecimalUtils.normalizeNumber(value);
                if (value.compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("Quantity cannot be less than zero");
                }
                break;
            case Uncountable:
                value = DecimalUtils.normalizeAmount(value);
                if (value.compareTo(DecimalUtils.ZERO_AMOUNT) < 0) {
                    throw new RuntimeException("Quantity cannot be less than zero");
                }
                break;
            default:
                throw new RuntimeException("unknown quantity category");
        }
        return value;
    }
}
