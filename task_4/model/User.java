package T1.homework.task_4.model;

public class User {
    private Long id;
    private String username;

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Пользователь {id=" + id + ", username='" + username + "'}";
    }
}