package ua.shop.orders.threads;

import ua.shop.orders.model.Electronics;
import ua.shop.orders.model.Order;
import ua.shop.orders.processing.OrderProcessor;
import ua.shop.orders.storage.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderWorkerTest {
    @Test
    void continuesAfterInvalidOrder() {
        Electronics product = Electronics.builder().name("Ноутбук").price(20000).build();
        Order<Electronics> invalidOrder = new Order<>(1, product, 0);
        Order<Electronics> validOrder = new Order<>(2, product, 1);
        OrderRepository repository = new OrderRepository();

        OrderWorker<Electronics> task = new OrderWorker<>(
                List.of(invalidOrder, validOrder), new OrderProcessor<>(), repository);
        task.run();

        assertEquals(1, repository.findAll().size());
        assertEquals(2, repository.findAll().get(0).getId());
    }
}
