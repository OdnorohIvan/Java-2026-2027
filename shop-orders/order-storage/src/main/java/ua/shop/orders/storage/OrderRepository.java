package ua.shop.orders.storage;

import ua.shop.orders.model.Order;
import ua.shop.orders.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRepository {
    private final List<Order<? extends Product>> orders = new ArrayList<>();

    public synchronized void store(Order<? extends Product> order) {
        orders.add(Objects.requireNonNull(order));
    }

    public synchronized List<Order<? extends Product>> findAll() {
        return new ArrayList<>(orders);
    }
}
