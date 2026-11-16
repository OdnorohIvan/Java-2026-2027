package ua.study.shop;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
public class OrderHistory {
    private final Path file;

    public void save(Order order) throws IOException {
        Files.writeString(file, order + System.lineSeparator().repeat(2), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public String read() throws IOException {
        if (Files.notExists(file)) {
            return "Історія замовлень порожня.";
        }
        return Files.readString(file, StandardCharsets.UTF_8);
    }
}
