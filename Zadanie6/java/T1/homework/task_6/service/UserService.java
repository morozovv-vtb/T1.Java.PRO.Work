package T1.homework.task_6.service;

import T1.homework.task_6.dto.UserDto;
import T1.homework.task_6.exception.UserNotFoundException;
import T1.homework.task_6.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }

    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new UserDto(user.getId(), user.getUsername()))
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }
}