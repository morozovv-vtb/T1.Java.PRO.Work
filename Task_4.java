package T1.homework.task_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

//@Component
class User {
    private Long id;
    private String username;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}

@Component
class UserDao {

    @Autowired
    private DataSource dataSource;

    public void createUser(String username) {
        String sql = "INSERT INTO users (username) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("v Пользователь создан с ID: " + rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("X Ошибка при создании пользователя: " + e.getMessage());
        }
    }

    public User findById(Long id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            System.err.println("X Ошибка при поиске пользователя: " + e.getMessage());
        }
        return null;
    }

    public List<User> findAll() {
        String sql = "SELECT id, username FROM users ORDER BY id";
        List<User> users = new java.util.ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("username")));
            }
        } catch (SQLException e) {
            System.err.println("X Ошибка при получении всех пользователей: " + e.getMessage());
        }
        return users;
    }

    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("v Пользователь с ID " + id + " удалён");
            } else {
                System.out.println("! Пользователь с ID " + id + " не найден");
            }
        } catch (SQLException e) {
            System.err.println("X Ошибка при удалении пользователя: " + e.getMessage());
        }
    }
}

@Component
class UserService {

    @Autowired
    private UserDao userDao;

    public void createUser(String username) {
        userDao.createUser(username);
    }

    public User getUser(Long id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    public void printAllUsers() {
        List<User> users = getAllUsers();
        System.out.println("\n<> Список всех пользователей:");
        users.stream()
                .sorted((u1, u2) -> Long.compare(u1.getId(), u2.getId()))
                .forEach(System.out::println);
    }
}

public class Task_4 {

    public static void main(String[] args) {
        // Запуск Spring Context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Создать таблицу, если её нет
        try (Connection conn = context.getBean(DataSource.class).getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "username VARCHAR(255) UNIQUE NOT NULL)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.execute();
                System.out.println("V Таблица 'users' создана или уже существует.");
            }
        } catch (SQLException e) {
            System.err.println("X Не удалось создать таблицу: " + e.getMessage());
        }

        // Получаем бин UserService
        UserService userService = context.getBean(UserService.class);

        System.out.println("^ Запущен Spring Context. Выполняем CRUD-операции...\n");

        // 1. Создание пользователей
        userService.createUser("Vladimir");
        userService.createUser("Vladislav");
        userService.createUser("Yaroslav");
        userService.createUser("Kseniya");
        userService.createUser("Juliya");

        // 2. Получение одного пользователя
        User user = userService.getUser(1L);
        System.out.println("\n=> Пользователь с ID=1: " + user);

        // 3. Получение всех пользователей
        userService.printAllUsers();

        // 4. Удаление пользователя
        userService.deleteUser(2L);

        // 5. Повторный вывод всех пользователей
        userService.printAllUsers();

        // 6. Пример использования Stream API: фильтрация по имени
        List<User> filtered = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().startsWith("V"))
                .collect(Collectors.toList());

        System.out.println("\n Пользователи с именем, начинающимся на 'V':");
        filtered.forEach(System.out::println);

        // Закрытие контекста
        context.close();
        System.out.println("\nV Задание выполнено. Spring Context закрыт.");
    }
}