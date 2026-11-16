package ua.shop.orders.processing;

import ua.shop.orders.model.Order;
import ua.shop.orders.model.Product;
import ua.shop.orders.processing.internal.OrderValidator;

public class OrderProcessor<T extends Product> {
    public void handle(Order<T> order) throws OrderProcessingException {
        OrderValidator.validate(order);
        System.out.println(Thread.currentThread().getName() + ": виконано " + order);
    }
}
