package T1.homework.task_6.runner;

import T1.homework.task_6.model.ProductEntity;
import T1.homework.task_6.model.UserEntity;
import T1.homework.task_6.service.ProductService;
import T1.homework.task_6.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;

    public DataInitializer(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> USER_NAMES = Arrays.asList(
                "Vladimir",
                "Vladislav",
                "Yaroslav",
                "Kseniya",
                "Juliya"
        );

        System.out.println("=== Начало выполнения задач ===");

        // Очистка и сброс индексов
        userService.clearAndReset();

        // Создание пользователей
        userService.createAllUsers(USER_NAMES);

        // Получаем всех пользователей для привязки продуктов
        List<UserEntity> users = userService.getAllUsers();

        // Создание продуктов для каждого пользователя
        for (UserEntity user : users) {
            // Каждому пользователю — 2 продукта
            productService.createProduct("ACC1001", new BigDecimal("5000.00"), "Счет", user.getId());
            productService.createProduct("CARD2001", new BigDecimal("2500.00"), "Карта", user.getId());
        }

        System.out.println("\nВсе пользователи:");
        for (UserEntity user : users) {
            System.out.println("User: " + user.getId() + " - " + user.getUsername());
        }

        // Проверка продуктов по userId
        List<ProductEntity> products = null;
        if (!users.isEmpty()) {
            Long userId = users.get(0).getId();
            System.out.println("\nПродукты пользователя " + userId + ":");
            products = productService.getAllProductsByUserId(userId);
            products.forEach(System.out::println);
        }

        // Проверка продукта по productId
        if (!products.isEmpty()) {
            ProductEntity product = products.get(0);
            System.out.println("\nПродукт по ID " + product.getId() + ": " + productService.getProductById(product.getId()).orElse(null));
        }

        System.out.println("=== Задачи выполнены ===");
    }
}