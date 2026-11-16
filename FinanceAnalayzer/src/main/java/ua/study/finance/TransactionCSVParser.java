package ua.study.finance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public final class TransactionCSVParser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter
            .ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    private TransactionCSVParser() {
    }

    public static List<Transaction> parse(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",", 3);
            if (values.length != 3) {
                throw new IllegalArgumentException("У рядку мають бути дата, сума й опис: " + line);
            }

            String date = values[0].trim();
            String description = values[2].trim();
            double amount;
            try {
                LocalDate.parse(date, DATE_FORMAT);
                amount = Double.parseDouble(values[1].trim());
            } catch (DateTimeParseException | NumberFormatException e) {
                throw new IllegalArgumentException("Некоректна дата або сума: " + line);
            }

            if (!Double.isFinite(amount) || description.isBlank()) {
                throw new IllegalArgumentException("Некоректна сума або порожній опис: " + line);
            }
            transactions.add(new Transaction(date, amount, description));
        }
        return transactions;
    }
}
