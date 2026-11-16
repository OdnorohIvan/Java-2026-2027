package ua.study.shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderHistoryTest {
    @TempDir
    Path directory;

    private Order createOrder(String name) {
        Cart cart = new Cart();
        cart.addProduct(new Product(1, name, 500, "Опис", new Category(1, "Електроніка")));
        return new Order(cart);
    }

    @Test
    void savesHistoryForAnotherInstance() throws IOException {
        Path file = directory.resolve("orders.txt");
        OrderHistory history = new OrderHistory(file);
        Order order = createOrder("Миша");
        history.save(order);

        String saved = new OrderHistory(file).read();
        assertTrue(saved.contains("Миша"));
        assertTrue(saved.contains("500.00 грн"));
        assertTrue(saved.contains("Статус: Нове"));
    }

    @Test
    void appendsOrdersWithoutDeletingEarlierOnes() throws IOException {
        OrderHistory history = new OrderHistory(directory.resolve("orders.txt"));
        history.save(createOrder("Миша"));
        history.save(createOrder("Клавіатура"));

        String saved = history.read();
        assertTrue(saved.contains("Миша"));
        assertTrue(saved.contains("Клавіатура"));
        assertEquals(2, saved.split("Замовлення:").length - 1);
    }

    @Test
    void reportsEmptyHistoryWhenFileDoesNotExist() throws IOException {
        OrderHistory history = new OrderHistory(directory.resolve("orders.txt"));
        assertEquals("Історія замовлень порожня.", history.read());
    }
}
