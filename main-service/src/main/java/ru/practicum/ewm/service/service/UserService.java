package ru.practicum.ewm.service.service;

import ru.practicum.ewm.service.model.User;

import java.util.List;


public interface UserService {
    User saveUser(User user);

    List<User> findUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);
}
