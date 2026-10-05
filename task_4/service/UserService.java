package T1.homework.task_4.service;

import T1.homework.task_4.dao.UserDao;
import T1.homework.task_4.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserDao userDao;

    public void createUsers(List<String> usernames) {
        userDao.createTable();
        userDao.clearAndReset();
        userDao.insertUsers(usernames);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Optional<User> getUserById(Long id) {
        User user = userDao.findById(id);
        return Optional.ofNullable(user);
    }

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    public void deleteAllUsers() {
        userDao.deleteAll();
    }
}