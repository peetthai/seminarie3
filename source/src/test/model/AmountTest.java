package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountTest {

    @Test
    void addTwoPositiveAmountsReturnsCorrectSum() {
        Amount a = new Amount(200);
        Amount b = new Amount(150);
        Amount result = a.add(b);
        assertEquals(350, result.getValue(), 0.001);
    }

    @Test
    void addZeroToAmountReturnsUnchangedValue() {
        Amount a = new Amount(500);
        Amount zero = new Amount(0);
        Amount result = a.add(zero);
        assertEquals(500, result.getValue(), 0.001);
    }

    @Test
    void addTwoDecimalAmountsReturnsCorrectSum() {
        Amount a = new Amount(99.99);
        Amount b = new Amount(0.01);
        Amount result = a.add(b);
        assertEquals(100.0, result.getValue(), 0.001);
    }

    @Test
    void subtractSmallerAmountReturnsPositiveDifference() {
        Amount a = new Amount(300);
        Amount b = new Amount(100);
        Amount result = a.subtract(b);
        assertEquals(200, result.getValue(), 0.001);
    }

    @Test
    void subtractEqualAmountReturnsZero() {
        Amount a = new Amount(250);
        Amount b = new Amount(250);
        Amount result = a.subtract(b);
        assertEquals(0, result.getValue(), 0.001);
    }

    @Test
    void getValueReturnsStoredValue() {
        Amount a = new Amount(123.45);
        assertEquals(123.45, a.getValue(), 0.001);
    }

    @Test
    void addIsNotMutatingOriginalAmount() {
        Amount original = new Amount(100);
        original.add(new Amount(50));
        assertEquals(100, original.getValue(), 0.001);
    }
}
