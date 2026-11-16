package ua.shop.orders.threads;

import ua.shop.orders.model.Clothing;
import ua.shop.orders.model.Electronics;
import ua.shop.orders.model.Order;
import ua.shop.orders.model.Product;
import ua.shop.orders.processing.OrderProcessor;
import ua.shop.orders.storage.OrderRepository;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ShopApplication {
    public static void main(String[] args) throws InterruptedException {
        Faker faker = new Faker(Locale.ENGLISH);
        OrderRepository repository = new OrderRepository();

        List<Order<Electronics>> electronicsOrders = buildElectronicsOrders(faker);
        List<Order<Clothing>> clothingOrders = buildClothingOrders(faker);
        electronicsOrders.add(new Order<>(11, electronicsOrders.get(0).getProduct(), -1));

        OrderWorker<Electronics> electronicsWorker = new OrderWorker<>(
                electronicsOrders, new OrderProcessor<>(), repository);
        OrderWorker<Clothing> clothingWorker = new OrderWorker<>(
                clothingOrders, new OrderProcessor<>(), repository);

        Thread electronicsThread = new Thread(() -> electronicsWorker.run(), "electronics-worker");
        Thread clothingThread = new Thread(() -> clothingWorker.run(), "clothing-worker");

        electronicsThread.start();
        clothingThread.start();
        electronicsThread.join();
        clothingThread.join();

        List<Order<? extends Product>> completedOrders = repository.findAll();
        System.out.println("\nУспішних замовлень: " + completedOrders.size());

        double total = completedOrders.stream().mapToDouble(Order::getTotalPrice).sum();
        System.out.printf(Locale.US, "Вартість усіх замовлень: %.2f грн%n", total);

        System.out.println("\nЗамовлення категорії Одяг:");
        completedOrders.stream()
                .filter(order -> order.getProduct() instanceof Clothing)
                .sorted(Comparator.comparingInt(Order::getId))
                .forEach(System.out::println);
    }

    private static List<Order<Electronics>> buildElectronicsOrders(Faker faker) {
        List<Order<Electronics>> orders = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Electronics product = Electronics.builder()
                    .name(faker.options().option("Навушники", "Клавіатура", "Планшет"))
                    .price(faker.number().numberBetween(700, 15001))
                    .brand(faker.company().name())
                    .warrantyMonths(faker.options().option(6, 12, 24))
                    .build();
            orders.add(new Order<>(i, product, faker.number().numberBetween(1, 3)));
        }
        return orders;
    }

    private static List<Order<Clothing>> buildClothingOrders(Faker faker) {
        List<Order<Clothing>> orders = new ArrayList<>();
        for (int i = 5; i <= 10; i++) {
            Clothing product = Clothing.builder()
                    .name(faker.options().option("Світшот", "Сорочка", "Спортивні штани"))
                    .price(faker.number().numberBetween(500, 4001))
                    .size(faker.options().option("S", "M", "L", "XL"))
                    .material(faker.commerce().material())
                    .build();
            orders.add(new Order<>(i, product, faker.number().numberBetween(1, 4)));
        }
        return orders;
    }
}
