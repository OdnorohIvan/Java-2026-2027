package ua.study.finance;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionAnalyzerTest {
    private final List<Transaction> transactions = List.of(
            new Transaction("01-12-2023", 10000, "Зарплата"),
            new Transaction("02-12-2023", -2000, "Продукти"),
            new Transaction("03-12-2023", -500, "Транспорт"),
            new Transaction("01-01-2024", -3000, "Продукти"),
            new Transaction("31-01-2024", -1000, "Транспорт"),
            new Transaction("01-02-2024", -9000, "Оренда"));

    @Test
    void calculatesTotalBalance() {
        assertEquals(-5500.0, TransactionAnalyzer.calculateTotalBalance(transactions), 0.001);
    }

    @Test
    void returnsZeroBalanceForEmptyList() {
        assertEquals(0.0, TransactionAnalyzer.calculateTotalBalance(List.of()), 0.001);
    }

    @Test
    void countsTransactionsByMonthAndYear() {
        assertEquals(3, TransactionAnalyzer.countTransactionsByMonth(transactions, "12-2023"));
        assertEquals(2, TransactionAnalyzer.countTransactionsByMonth(transactions, "01-2024"));
        assertEquals(0, TransactionAnalyzer.countTransactionsByMonth(transactions, "01-2023"));
    }

    @Test
    void returnsTenLargestExpenses() {
        List<Transaction> data = new ArrayList<>();
        data.add(new Transaction("01-01-2024", 10000, "Дохід"));
        data.add(new Transaction("01-01-2024", 0, "Нуль"));
        for (int i = 1; i <= 12; i++) {
            data.add(new Transaction("01-01-2024", -i * 100, "Витрата " + i));
        }

        List<Transaction> top = TransactionAnalyzer.findTopExpenses(data);
        assertEquals(10, top.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(-1200.0 + i * 100, top.get(i).getAmount(), 0.001);
        }
    }

    @Test
    void returnsAllExpensesWhenThereAreFewerThanTen() {
        assertEquals(5, TransactionAnalyzer.findTopExpenses(transactions).size());
        assertTrue(TransactionAnalyzer.findTopExpenses(List.of()).isEmpty());
    }

    @Test
    void findsLargestAndSmallestExpenseWithinInclusivePeriod() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);
        Transaction largest = TransactionAnalyzer.findLargestExpense(transactions, start, end);
        Transaction smallest = TransactionAnalyzer.findSmallestExpense(transactions, start, end);

        assertEquals(-3000.0, largest.getAmount(), 0.001);
        assertEquals("01-01-2024", largest.getDate());
        assertEquals(-1000.0, smallest.getAmount(), 0.001);
        assertEquals("31-01-2024", smallest.getDate());
    }

    @Test
    void returnsNullWhenPeriodHasNoExpenses() {
        LocalDate day = LocalDate.of(2023, 12, 1);
        assertNull(TransactionAnalyzer.findLargestExpense(transactions, day, day));
        assertNull(TransactionAnalyzer.findSmallestExpense(transactions, day, day));
    }

    @Test
    void rejectsReversedPeriod() {
        LocalDate start = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2024, 1, 1);
        assertThrows(IllegalArgumentException.class,
                () -> TransactionAnalyzer.findLargestExpense(transactions, start, end));
    }

    @Test
    void sumsExpensesByCategoryWithoutIncome() {
        Map<String, Double> totals = TransactionAnalyzer.calculateExpensesByCategory(transactions);
        assertEquals(Map.of("Продукти", 5000.0, "Транспорт", 1500.0, "Оренда", 9000.0), totals);
    }

    @Test
    void sumsExpensesByMonthInChronologicalOrder() {
        Map<String, Double> totals = TransactionAnalyzer.calculateExpensesByMonth(transactions);
        assertEquals(Map.of("2023-12", 2500.0, "2024-01", 4000.0, "2024-02", 9000.0), totals);
        assertEquals(List.of("2023-12", "2024-01", "2024-02"), new ArrayList<>(totals.keySet()));
    }
}
