package ua.study.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    @Test
    void readsNumberWithSpaces() throws InvalidInputException {
        assertEquals(12.5, Main.parseNumber(" 12.5 "), 0.000001);
    }

    @Test
    void readsNumberWithComma() throws InvalidInputException {
        assertEquals(-2.5, Main.parseNumber("-2,5"), 0.000001);
    }

    @Test
    void rejectsTextInsteadOfNumber() {
        assertThrows(InvalidInputException.class, () -> Main.parseNumber("hello"));
    }

    @Test
    void rejectsEmptyInput() {
        assertThrows(InvalidInputException.class, () -> Main.parseNumber(" "));
    }
}
