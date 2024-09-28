package ru.pas_zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.exception.request.LoginNotUniqueException;
import ru.pas_zhukov.exception.request.UserNotFoundException;

import java.util.*;

@Component
public class UserService {

    private AccountService accountService;

    private int idCounter = 0;

    // Volume
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> userNames = new HashSet<>();

    @Autowired
    public void setAccountService(AccountService accountService) {
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

    public void deleteAccount(User user, Account account) {
        user.removeAccount(account);
        accountService.deleteAccount(account);
    }

    public void deleteAccount(User user, int accountId) {
        user.removeAccount(accountId);
        accountService.deleteAccount(accountId);
    }

    public void deleteAccount(int userId, int accountId) {
        User user = getUserById(userId);
        deleteAccount(user, accountId);
    }

    public void deleteAccount(Account account) {
        User user = getUserById(account.getUserId());
        deleteAccount(user, account);
    }

    public void deleteAccount(int accountId) {
        User user = getUserById(accountService.getAccountById(accountId).getUserId());
        deleteAccount(user, accountId);
    }

}
