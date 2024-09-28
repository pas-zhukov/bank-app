package ru.pas_zhukov.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.entity.User;

import java.util.*;

@Component
public class UserService {

    private final AccountService accountService;

    private int idCounter = 0;

    // Volume
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> userNames = new HashSet<>();

    @Lazy // временное решение этапа без БД
    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public User createUser(String username) {
        if (userNames.contains(username)) {
            throw new IllegalArgumentException("User with login " + username + " already exists. Please choose another username.");
        }
        User user = new User(idCounter, username);
        users.put(idCounter, user);
        userNames.add(username);
        accountService.createAccount(user);
        idCounter++;
        return user;
    }

    public User createUser() {
        return createUser("User" + idCounter);
    }

    public User getUserById(int id) throws IllegalArgumentException {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new IllegalArgumentException("User with id " + id + " not found");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

}
