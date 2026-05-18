package model;

/**
 * Represents a monetary amount. Immutable; all arithmetic returns new instances.
 */
public class Amount {
    private final double value;

    /**
     * Creates an Amount with the specified value.
     *
     * @param value The monetary value.
     */
    public Amount(double value) {
        this.value = value;
    }

    /**
     * Returns a new Amount that is the sum of this amount and the given amount.
     *
     * @param other The amount to add.
     * @return A new Amount representing the sum.
     */
    public Amount add(Amount other) {
        return new Amount(this.value + other.value);
    }

    /**
     * Returns a new Amount that is the difference of this amount minus the given amount.
     *
     * @param other The amount to subtract.
     * @return A new Amount representing the difference.
     */
    public Amount subtract(Amount other) {
        return new Amount(this.value - other.value);
    }

    /**
     * Returns the raw numeric value of this amount.
     *
     * @return The monetary value as a double.
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns a formatted string representation of this amount.
     *
     * @return A string in the form {@code "123.45 SEK"}.
     */
    @Override
    public String toString() {
        return String.format("%.2f SEK", value);
    }
}
