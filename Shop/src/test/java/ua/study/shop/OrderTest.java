package ua.study.shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {
    private Cart createCart() {
        Cart cart = new Cart();
        Category category = new Category(1, "Електроніка");
        cart.addProduct(new Product(1, "Ноутбук", 10000, "Опис", category));
        cart.addProduct(new Product(2, "Миша", 500, "Опис", category));
        return cart;
    }

    @Test
    void keepsProductsAndTotalAfterCartIsCleared() {
        Cart cart = createCart();
        Order order = new Order(cart);
        cart.clear();

        assertEquals(2, order.getProducts().size());
        assertEquals(10500.0, order.getTotalPrice(), 0.001);
        assertEquals("Нове", order.getStatus());
    }

    @Test
    void returnsCopyOfOrderProducts() {
        Order order = new Order(createCart());
        order.getProducts().clear();

        assertEquals(2, order.getProducts().size());
    }

    @Test
    void changesStatusWithLombokSetter() {
        Order order = new Order(createCart());
        order.setStatus("Оплачене");

        assertEquals("Оплачене", order.getStatus());
    }

    @Test
    void rejectsEmptyCart() {
        assertThrows(IllegalArgumentException.class, () -> new Order(new Cart()));
    }
}
