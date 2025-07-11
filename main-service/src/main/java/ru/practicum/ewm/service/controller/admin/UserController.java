package ru.practicum.ewm.service.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.model.User;
import ru.practicum.ewm.service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final UserService userService;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user, HttpServletRequest request) {
        log.info("Создание пользователя: {}", user);
        statsClient.saveHit(requestUtils.createHit(request));
        return userService.saveUser(user);
    }

    @GetMapping("/users")
    public List<User> findUsers(@RequestParam List<Integer> ids,
                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                @RequestParam(name = "size", defaultValue = "10") Integer size,
                                HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        log.info("Поиск пользователей с ids: {}, from: {}, size: {}", ids, from, size);
        return userService.findUsers(ids, from, size);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Integer userId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        log.info("Удаление пользователя с id: {}", userId);
        userService.deleteUser(userId);
    }
}
