package ua.shop.orders.threads;

import ua.shop.orders.model.Order;
import ua.shop.orders.model.Product;
import ua.shop.orders.processing.OrderProcessingException;
import ua.shop.orders.processing.OrderProcessor;
import ua.shop.orders.storage.OrderRepository;

import java.util.List;

class OrderWorker<T extends Product> implements Runnable {
    private final List<Order<T>> orders;
    private final OrderProcessor<T> processor;
    private final OrderRepository repository;

    OrderWorker(List<Order<T>> orders, OrderProcessor<T> processor, OrderRepository repository) {
        this.orders = orders;
        this.processor = processor;
        this.repository = repository;
    }

    @Override
    public void run() {
        orders.forEach(this::handleOrder);
    }

    private void handleOrder(Order<T> order) {
        try {
            processor.handle(order);
            repository.store(order);
        } catch (OrderProcessingException e) {
            System.out.println(Thread.currentThread().getName() + ": не вдалося обробити замовлення: " + e.getMessage());
        }
    }
}
