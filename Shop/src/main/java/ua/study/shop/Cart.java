package ua.study.shop;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor
public class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Кошик порожній.";
        }
        StringBuilder result = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            result.append(product).append("\n");
        }
        result.append(String.format(Locale.US, "Загальна вартість: %.2f грн", getTotalPrice()));
        return result.toString();
    }
}
