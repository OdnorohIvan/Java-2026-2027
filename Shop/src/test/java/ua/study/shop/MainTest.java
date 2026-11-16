package ua.study.shop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private final List<Product> catalog = List.of(
            new Product(1, "Ноутбук", 10000, "Опис", new Category(1, "Електроніка")),
            new Product(2, "Смартфон", 5000, "Опис", new Category(2, "Смартфони")));

    @Test
    void searchesByPartOfNameWithoutCaseSensitivity() {
        List<Product> found = Main.searchProducts(catalog, "  НОУТ  ");
        assertEquals(1, found.size());
        assertEquals(1, found.get(0).getId());
    }

    @Test
    void searchesByCategory() {
        List<Product> found = Main.searchProducts(catalog, "електроніка");
        assertEquals(1, found.size());
        assertEquals(1, found.get(0).getId());
    }

    @Test
    void returnsNoResultsForUnknownOrEmptyQuery() {
        assertTrue(Main.searchProducts(catalog, "телевізор").isEmpty());
        assertTrue(Main.searchProducts(catalog, " ").isEmpty());
    }
}
