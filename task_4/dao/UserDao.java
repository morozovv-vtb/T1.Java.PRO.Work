package T1.homework.task_4.dao;

import T1.homework.task_4.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UserDao {

    @Autowired
    private DataSource dataSource;

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGSERIAL PRIMARY KEY,
                username VARCHAR(255) UNIQUE
            )
            """;
        executeUpdate(sql);
    }

    public void clearAndReset() {
        executeUpdate("DELETE FROM users");
        executeUpdate("ALTER SEQUENCE users_id_seq RESTART WITH 1");
    }

    public void insertUsers(List<String> usernames) {
        String sql = "INSERT INTO users (username) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String username : usernames) {
                ps.setString(1, username);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert users", e);
        }
    }

    public List<User> findAll() {
        String sql = "SELECT id, username FROM users ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Используем Stream API правильно: обходим ResultSet вручную
            return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(
                                    new Iterator<User>() {
                                        @Override
                                        public boolean hasNext() {
                                            try {
                                                return rs.next();
                                            } catch (SQLException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }

                                        @Override
                                        public User next() {
                                            try {
                                                return new User(rs.getLong("id"), rs.getString("username"));
                                            } catch (SQLException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    },
                                    Spliterator.ORDERED
                            ),
                            false
                    )
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch users", e);
        }
    }
    public User findById(Long id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by id", e);
        }
        return null;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdate(sql, id);
    }

    public void deleteAll() {
        clearAndReset();
    }

    private void executeUpdate(String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + sql, e);
        }
    }

    private void executeUpdate(String sql) {
        executeUpdate(sql, new Object[0]);
    }
}