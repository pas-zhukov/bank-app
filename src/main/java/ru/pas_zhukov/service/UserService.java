package ru.pas_zhukov.service;

import org.springframework.stereotype.Component;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.exception.UserNotFoundException;

import java.util.*;

@Component
public class UserService {

    private int idCounter = 0;

    // Volume
    private final List<User> users = new ArrayList<>();

    public User createUser() {
        User user = new User(idCounter, "User #" + idCounter); // TODO: login
        idCounter++;
        users.add(user);
        return user;
    }

    public User getUserById(int id) throws UserNotFoundException {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        throw new UserNotFoundException();
    }

    public List<User> getAllUsers() {
        return users;
    }

}
