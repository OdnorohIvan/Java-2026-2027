package ua.study.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();
    private static final double DELTA = 0.000001;

    @Test
    void addsNumbers() throws InvalidInputException {
        assertEquals(7.5, calculator.add(5, 2.5), DELTA);
    }

    @Test
    void subtractsNumbers() throws InvalidInputException {
        assertEquals(-3.0, calculator.subtract(2, 5), DELTA);
    }

    @Test
    void multipliesNumbers() throws InvalidInputException {
        assertEquals(-6.0, calculator.multiply(-2, 3), DELTA);
    }

    @Test
    void dividesNumbers() throws InvalidInputException {
        assertEquals(2.5, calculator.divide(5, 2), DELTA);
    }

    @Test
    void allowsZeroInOtherOperations() throws InvalidInputException {
        assertEquals(4.0, calculator.add(0, 4), DELTA);
        assertEquals(-4.0, calculator.subtract(0, 4), DELTA);
        assertEquals(0.0, calculator.multiply(0, 4), DELTA);
        assertEquals(0.0, calculator.divide(0, 4), DELTA);
    }

    @Test
    void rejectsDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, -0.0));
    }

    @Test
    void rejectsNaN() {
        assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 1));
        assertThrows(InvalidInputException.class, () -> calculator.subtract(1, Double.NaN));
        assertThrows(InvalidInputException.class, () -> calculator.multiply(Double.NaN, 1));
        assertThrows(InvalidInputException.class, () -> calculator.divide(1, Double.NaN));
    }

    @Test
    void rejectsInfinity() {
        assertThrows(InvalidInputException.class, () -> calculator.add(1, Double.POSITIVE_INFINITY));
        assertThrows(InvalidInputException.class, () -> calculator.subtract(Double.NEGATIVE_INFINITY, 1));
        assertThrows(InvalidInputException.class, () -> calculator.multiply(1, Double.NEGATIVE_INFINITY));
        assertThrows(InvalidInputException.class, () -> calculator.divide(Double.POSITIVE_INFINITY, 1));
    }
}
