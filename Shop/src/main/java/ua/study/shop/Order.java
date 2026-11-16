package ua.study.shop;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
public class Order {
    private final List<Product> products;
    private final double totalPrice;
    @Setter
    private String status;

    public Order(Cart cart) {
        products = cart.getProducts();
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Не можна оформити порожнє замовлення.");
        }
        totalPrice = cart.getTotalPrice();
        status = "Нове";
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Замовлення:\n");
        for (Product product : products) {
            result.append(product).append("\n");
        }
        result.append(String.format(Locale.US, "Загальна вартість: %.2f грн%nСтатус: %s",
                totalPrice, status));
        return result.toString();
    }
}
