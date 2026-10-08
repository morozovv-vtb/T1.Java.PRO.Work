package T1.homework.task_5.runner;

import T1.homework.task_5.model.UserEntity;
import T1.homework.task_5.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
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

        // Создание всех пользователей
        userService.createAllUsers(USER_NAMES);

        // Получение всех пользователей
        System.out.println("Все пользователи:");
        userService.getAllUsers().forEach(System.out::println);

        // Получение одного пользователя по ID
        List<UserEntity> allUsers = userService.getAllUsers();
        if (!allUsers.isEmpty()) {
            UserEntity firstUser = allUsers.get(0);
            UserEntity foundUser = userService.getUserById(firstUser.getId());
            System.out.println("\nПолучен пользователь по ID " + firstUser.getId() + ": " + foundUser);
        }

        // Удаление пользователя
        if (!allUsers.isEmpty()) {
            UserEntity userToDelete = allUsers.get(1);
            userService.deleteUser(userToDelete.getId());
            System.out.println("\nПользователь с ID " + userToDelete.getId() + " удалён.");
        }

        // Проверка оставшихся
        System.out.println("\nОставшиеся пользователи:");
        userService.getAllUsers().forEach(System.out::println);

        System.out.println("=== Задачи выполнены ===");
    }
}