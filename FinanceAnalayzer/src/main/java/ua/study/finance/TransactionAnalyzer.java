package ua.study.finance;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter
            .ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MM-uuuu");

    private TransactionAnalyzer() {
    }

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        YearMonth month = YearMonth.parse(monthYear, MONTH_FORMAT);
        int count = 0;
        for (Transaction transaction : transactions) {
            if (YearMonth.from(parseDate(transaction)).equals(month)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(10)
                .toList();
    }

    public static Transaction findLargestExpense(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return findExpensesByPeriod(transactions, start, end).stream()
                .min(Comparator.comparingDouble(Transaction::getAmount))
                .orElse(null);
    }

    public static Transaction findSmallestExpense(List<Transaction> transactions, LocalDate start, LocalDate end) {
        return findExpensesByPeriod(transactions, start, end).stream()
                .max(Comparator.comparingDouble(Transaction::getAmount))
                .orElse(null);
    }

    public static Map<String, Double> calculateExpensesByCategory(List<Transaction> transactions) {
        Map<String, Double> totals = new LinkedHashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                String category = transaction.getDescription();
                totals.put(category, totals.getOrDefault(category, 0.0) - transaction.getAmount());
            }
        }
        return totals;
    }

    public static Map<String, Double> calculateExpensesByMonth(List<Transaction> transactions) {
        Map<String, Double> totals = new TreeMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                String month = YearMonth.from(parseDate(transaction)).toString();
                totals.put(month, totals.getOrDefault(month, 0.0) - transaction.getAmount());
            }
        }
        return totals;
    }

    private static List<Transaction> findExpensesByPeriod(List<Transaction> transactions, LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Початок періоду не може бути пізніше за кінець.");
        }
        List<Transaction> expenses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate date = parseDate(transaction);
            if (transaction.getAmount() < 0 && !date.isBefore(start) && !date.isAfter(end)) {
                expenses.add(transaction);
            }
        }
        return expenses;
    }

    private static LocalDate parseDate(Transaction transaction) {
        return LocalDate.parse(transaction.getDate(), DATE_FORMAT);
    }
}
