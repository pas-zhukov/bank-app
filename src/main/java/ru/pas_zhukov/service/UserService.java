package ru.pas_zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.exception.request.LoginNotUniqueException;
import ru.pas_zhukov.exception.request.UserNotFoundException;

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
        if (userNames.contains(username)) throw new LoginNotUniqueException(username);
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

    public User getUserById(int id) throws UserNotFoundException {
        if (users.containsKey(id)) return users.get(id);
        throw new UserNotFoundException(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

}
