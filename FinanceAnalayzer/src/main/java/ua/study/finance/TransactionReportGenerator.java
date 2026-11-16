package ua.study.finance;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class TransactionReportGenerator {
    private TransactionReportGenerator() {
    }

    public static void printTransactions(List<Transaction> transactions) {
        System.out.println("Усі транзакції:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void printBalanceReport(double totalBalance) {
        System.out.printf(Locale.US, "\nЗагальний баланс: %.2f грн%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("\n10 найбільших витрат:");
        for (Transaction transaction : topExpenses) {
            printExpense(transaction);
        }
    }

    public static void printExpenseRangeReport(LocalDate start, LocalDate end,
                                               Transaction largest, Transaction smallest) {
        System.out.println("\nВитрати за період " + start + " - " + end + ":");
        if (largest == null || smallest == null) {
            System.out.println("За цей період витрат немає.");
            return;
        }
        System.out.print("Найбільша витрата: ");
        printExpense(largest);
        System.out.print("Найменша витрата: ");
        printExpense(smallest);
    }

    public static void printExpensesByCategory(Map<String, Double> totals) {
        System.out.println("\nСумарні витрати за категоріями, від найбільших:");
        printChartLegend();
        totals.entrySet().stream()
                .sorted((first, second) -> Double.compare(second.getValue(), first.getValue()))
                .forEach(entry -> printChartLine(entry.getKey(), entry.getValue()));
    }

    public static void printExpensesByMonth(Map<String, Double> totals) {
        System.out.println("\nСумарні витрати за місяцями:");
        printChartLegend();
        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            printChartLine(entry.getKey(), entry.getValue());
        }
    }

    private static void printExpense(Transaction transaction) {
        System.out.printf(Locale.US, "%s | %s | %.2f грн%n",
                transaction.getDate(), transaction.getDescription(), transaction.getAmount());
    }

    private static void printChartLegend() {
        System.out.println("Одна * = 1000 грн. Залишок менше 1000 грн не позначається.");
    }

    private static void printChartLine(String label, double amount) {
        String stars = "*".repeat((int) (amount / 1000));
        System.out.printf(Locale.US, "%s: %.2f грн %s%n", label, amount, stars);
    }
}
