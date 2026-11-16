package ua.study.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category category;

    @Override
    public String toString() {
        return String.format(Locale.US, "%d. %s, %.2f грн (%s) - %s",
                id, name, price, category.getName(), description);
    }
}
