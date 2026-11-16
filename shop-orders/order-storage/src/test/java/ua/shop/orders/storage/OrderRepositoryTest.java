package ua.shop.orders.storage;

import ua.shop.orders.model.Electronics;
import ua.shop.orders.model.Order;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderRepositoryTest {
    private final Electronics product = Electronics.builder().name("Монітор").price(5000).build();

    @Test
    void storesAllOrdersFromTwoThreads() throws InterruptedException {
        OrderRepository repository = new OrderRepository();
        CountDownLatch start = new CountDownLatch(1);

        Thread first = new Thread(() -> storeOrders(repository, start, 1));
        Thread second = new Thread(() -> storeOrders(repository, start, 501));
        first.start();
        second.start();
        start.countDown();
        first.join();
        second.join();

        assertEquals(1000, repository.findAll().size());
        assertEquals(1000L, repository.findAll().stream().map(Order::getId).distinct().count());
    }

    @Test
    void returnsCopyOfStoredOrders() {
        OrderRepository repository = new OrderRepository();
        repository.store(new Order<>(1, product, 1));
        repository.findAll().clear();
        assertEquals(1, repository.findAll().size());
    }

    private void storeOrders(OrderRepository repository, CountDownLatch start, int firstId) {
        try {
            start.await();
            for (int i = firstId; i < firstId + 500; i++) {
                repository.store(new Order<>(i, product, 1));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
