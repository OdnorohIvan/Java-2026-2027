package ua.study.finance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionCSVReaderTest {
    @TempDir
    Path directory;

    @Test
    void readsTransactionsFromCsvFile() throws IOException {
        Path file = directory.resolve("transactions.csv");
        Files.write(file, List.of("01-01-2024,2000,Зарплата", "02-01-2024,-500,Аптека"),
                StandardCharsets.UTF_8);

        List<Transaction> transactions = TransactionCSVReader.readTransactions(file.toUri().toURL());
        assertEquals(2, transactions.size());
        assertEquals("Аптека", transactions.get(1).getDescription());
        assertEquals(-500.0, transactions.get(1).getAmount(), 0.001);
    }

    @Test
    void readsRawLinesIndependentlyOfCsvFormat() throws IOException {
        Path file = directory.resolve("text.txt");
        List<String> lines = List.of("Звичайний текст", "Це ще не транзакції");
        Files.write(file, lines, StandardCharsets.UTF_8);

        assertEquals(lines, TransactionCSVReader.readLines(file.toUri().toURL()));
    }
}
