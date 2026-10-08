package T1.homework.task_5.service;

import T1.homework.task_5.model.UserEntity;
import T1.homework.task_5.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createAllUsers(List<String> userNames) {
        for (String username : userNames) {
            createUser(username);
        }
    }

    @Transactional
    public void createUser(String username) {
        UserEntity user = new UserEntity(username);
        userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void clearAndReset() {
        userRepository.deleteAll(); // Очистка данных
        userRepository.resetSequence(); // Сброс последовательности — теперь безопасно!
    }
}