package ua.study.finance;

import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            URL source = Main.class.getResource("/pr2.csv");
            List<Transaction> transactions = TransactionCSVReader.readTransactions(source);

            TransactionReportGenerator.printTransactions(transactions);
            TransactionReportGenerator.printBalanceReport(
                    TransactionAnalyzer.calculateTotalBalance(transactions));

            String monthYear = "01-2024";
            TransactionReportGenerator.printTransactionsCountByMonth(monthYear,
                    TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear));
            TransactionReportGenerator.printTopExpensesReport(
                    TransactionAnalyzer.findTopExpenses(transactions));

            LocalDate start = LocalDate.of(2024, 1, 1);
            LocalDate end = LocalDate.of(2024, 1, 31);
            TransactionReportGenerator.printExpenseRangeReport(start, end,
                    TransactionAnalyzer.findLargestExpense(transactions, start, end),
                    TransactionAnalyzer.findSmallestExpense(transactions, start, end));

            TransactionReportGenerator.printExpensesByCategory(
                    TransactionAnalyzer.calculateExpensesByCategory(transactions));
            TransactionReportGenerator.printExpensesByMonth(
                    TransactionAnalyzer.calculateExpensesByMonth(transactions));
        } catch (IOException | IllegalArgumentException | DateTimeException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
