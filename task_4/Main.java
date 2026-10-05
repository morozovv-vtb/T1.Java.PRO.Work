package T1.homework.task_4;

import T1.homework.task_4.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

@ComponentScan(basePackages = "T1.homework.task_4")
@Configuration
public class Main {

    public static void main(String[] args) {
        // Создаём Spring Context с аннотационной конфигурацией
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        // Получаем бин UserService
        UserService userService = context.getBean(UserService.class);

        // Список имён пользователей
        List<String> usernames = Arrays.asList("Vladimir", "Vladislav", "Yaroslav", "Kseniya", "Juliya");

        // 1. Создать пользователей
        System.out.println("=== Создание пользователей ===");
        userService.createUsers(usernames);

        // 2. Получить всех пользователей
        System.out.println("\n=== Все пользователи ===");
        userService.getAllUsers().forEach(System.out::println);

        // 3. Получить одного пользователя по ID
        System.out.println("\n=== Пользователь с ID=1 ===");
        userService.getUserById(1L).ifPresent(System.out::println);

        // 4. Удалить одного пользователя
        System.out.println("\n=== Удаление пользователя с ID=2 ===");
        userService.deleteUser(2L);

        // 5. Проверить, что пользователь удалён
        System.out.println("\n=== Пользователи после удаления ===");
        userService.getAllUsers().forEach(System.out::println);

/*
        // 6. Удалить всех пользователей
        System.out.println("\n=== Удаление всех пользователей ===");
        userService.deleteAllUsers();

        // 7. Проверить, что таблица пуста
        System.out.println("\n=== Все пользователи после очистки ===");
        System.out.println(userService.getAllUsers()); // должно быть []
*/
    }
}