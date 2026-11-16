package ua.study.shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartTest {
    private final Category category = new Category(1, "Електроніка");
    private final Product laptop = new Product(1, "Ноутбук", 19999.99, "Опис", category);
    private final Product headphones = new Product(2, "Навушники", 2499.00, "Опис", category);

    @Test
    void addsProductsAndCalculatesTotal() {
        Cart cart = new Cart();
        cart.addProduct(laptop);
        cart.addProduct(headphones);

        assertEquals(2, cart.getProducts().size());
        assertEquals(22498.99, cart.getTotalPrice(), 0.001);
    }

    @Test
    void removesOnlyOneCopyOfProduct() {
        Cart cart = new Cart();
        cart.addProduct(laptop);
        cart.addProduct(laptop);

        assertTrue(cart.removeProduct(laptop));
        assertEquals(1, cart.getProducts().size());
        assertEquals(19999.99, cart.getTotalPrice(), 0.001);
    }

    @Test
    void returnsCopyOfProducts() {
        Cart cart = new Cart();
        cart.addProduct(laptop);
        cart.getProducts().clear();

        assertEquals(1, cart.getProducts().size());
    }

    @Test
    void clearsCart() {
        Cart cart = new Cart();
        cart.addProduct(laptop);
        cart.clear();

        assertTrue(cart.getProducts().isEmpty());
        assertEquals(0.0, cart.getTotalPrice(), 0.001);
    }
}
