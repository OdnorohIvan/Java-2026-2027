package ua.study.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {
    private TransactionCSVReader() {
    }

    public static List<String> readLines(URL source) throws IOException {
        if (source == null) {
            throw new IOException("CSV-файл не знайдено.");
        }

        URLConnection connection = source.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static List<Transaction> readTransactions(URL source) throws IOException {
        return TransactionCSVParser.parse(readLines(source));
    }
}
