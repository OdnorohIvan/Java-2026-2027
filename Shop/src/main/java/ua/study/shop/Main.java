package ua.study.shop;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        List<Product> catalog = List.of(
                new Product(1, "Ноутбук", 19999.99, "Ноутбук для роботи та навчання", electronics),
                new Product(2, "Смартфон", 12999.50, "Смартфон із великим екраном", smartphones),
                new Product(3, "Навушники", 2499.00, "Бездротові навушники", accessories));

        Cart cart = new Cart();
        OrderHistory history = new OrderHistory(Path.of("orders.txt"));

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                if (!scanner.hasNextLine()) {
                    break;
                }
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1":
                            printProducts(catalog);
                            break;
                        case "2":
                            System.out.print("Введіть ID товару: ");
                            int addId = Integer.parseInt(scanner.nextLine().trim());
                            Product product = findProduct(catalog, addId);
                            if (product == null) {
                                System.out.println("Товар з таким ID не знайдено.");
                            } else {
                                cart.addProduct(product);
                                System.out.println("Товар додано до кошика.");
                            }
                            break;
                        case "3":
                            System.out.println(cart);
                            break;
                        case "4":
                            if (cart.getProducts().isEmpty()) {
                                System.out.println("Кошик порожній. Додайте товари перед оформленням.");
                            } else {
                                Order order = new Order(cart);
                                history.save(order);
                                System.out.println("Замовлення оформлено:");
                                System.out.println(order);
                                cart.clear();
                            }
                            break;
                        case "5":
                            System.out.print("Введіть ID товару для видалення: ");
                            int removeId = Integer.parseInt(scanner.nextLine().trim());
                            Product selected = findProduct(cart.getProducts(), removeId);
                            if (selected == null) {
                                System.out.println("У кошику немає такого товару.");
                            } else {
                                cart.removeProduct(selected);
                                System.out.println("Товар видалено з кошика.");
                            }
                            break;
                        case "6":
                            System.out.println(history.read());
                            break;
                        case "7":
                            System.out.print("Введіть назву товару або категорію: ");
                            printProducts(searchProducts(catalog, scanner.nextLine()));
                            break;
                        case "0":
                            System.out.println("Дякуємо за відвідування магазину!");
                            running = false;
                            break;
                        default:
                            System.out.println("Невідома опція. Спробуйте ще раз.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID товару має бути цілим числом.");
                } catch (NoSuchElementException e) {
                    System.out.println("Введення завершено.");
                    break;
                } catch (IOException e) {
                    System.out.println("Не вдалося прочитати або зберегти історію замовлень.");
                }
            }
        }
    }

    static List<Product> searchProducts(List<Product> catalog, String query) {
        String text = query.trim().toLowerCase(Locale.ROOT);
        List<Product> found = new ArrayList<>();
        if (text.isEmpty()) {
            return found;
        }
        for (Product product : catalog) {
            if (product.getName().toLowerCase(Locale.ROOT).contains(text)
                    || product.getCategory().getName().toLowerCase(Locale.ROOT).contains(text)) {
                found.add(product);
            }
        }
        return found;
    }

    private static Product findProduct(List<Product> products, int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    private static void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Товарів не знайдено.");
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void printMenu() {
        System.out.println("\n1 - Переглянути каталог");
        System.out.println("2 - Додати товар до кошика");
        System.out.println("3 - Переглянути кошик");
        System.out.println("4 - Оформити замовлення");
        System.out.println("5 - Видалити товар із кошика");
        System.out.println("6 - Історія замовлень");
        System.out.println("7 - Пошук товарів");
        System.out.println("0 - Вийти");
        System.out.print("Ваш вибір: ");
    }
}
