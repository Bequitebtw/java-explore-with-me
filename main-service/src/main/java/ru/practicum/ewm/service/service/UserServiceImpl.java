package ru.practicum.ewm.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.exception.UserNotFoundException;
import ru.practicum.ewm.service.model.User;
import ru.practicum.ewm.service.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findUsers(List<Integer> ids, Integer from, Integer size) {
        return userRepository.findByIdIn(ids, PageRequest.of(from / size, size));
    }

    @Transactional
    @Override
    public void deleteUser(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.deleteById(userId);
    }
}
