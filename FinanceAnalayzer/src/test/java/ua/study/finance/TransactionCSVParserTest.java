package ua.study.finance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionCSVParserTest {
    @Test
    void parsesValidRowsAndSkipsEmptyLines() {
        List<Transaction> transactions = TransactionCSVParser.parse(List.of(
                "01-12-2023,1000,Дохід", " ", "02-12-2023,-250, Продукти "));

        assertEquals(2, transactions.size());
        assertEquals("01-12-2023", transactions.get(0).getDate());
        assertEquals(-250.0, transactions.get(1).getAmount(), 0.001);
        assertEquals("Продукти", transactions.get(1).getDescription());
    }

    @Test
    void rejectsMissingDescriptionColumn() {
        assertThrows(IllegalArgumentException.class,
                () -> TransactionCSVParser.parse(List.of("01-12-2023,-100")));
    }

    @Test
    void rejectsInvalidAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> TransactionCSVParser.parse(List.of("01-12-2023,abc,Продукти")));
        assertThrows(IllegalArgumentException.class,
                () -> TransactionCSVParser.parse(List.of("01-12-2023,NaN,Продукти")));
    }

    @Test
    void rejectsImpossibleDate() {
        assertThrows(IllegalArgumentException.class,
                () -> TransactionCSVParser.parse(List.of("31-02-2024,-100,Продукти")));
    }
}
