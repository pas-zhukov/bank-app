package ru.pas_zhukov.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.config.HibernateConfiguration;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.util.TransactionHelper;

import java.util.*;

@Component
public class UserService {

    private final AccountService accountService;
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public UserService(AccountService accountService, TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.accountService = accountService;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public User createUser(String username) {
//        if (userNames.contains(username)) {
//            throw new IllegalArgumentException("User with login " + username + " already exists. Please choose another username.");
//        }
        return transactionHelper.executeInTransaction(() ->
        {
            Session session = sessionFactory.getCurrentSession();
            User user = new User(username);
            session.persist(user);
            accountService.createAccount(user);
            session.flush();
            session.refresh(user);
            return user;
        });
    }

    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);

//        throw new IllegalArgumentException("User with id " + id + " not found");
    }

    public List<User> getAllUsers() {
        return transactionHelper.executeInTransaction(() -> {
            try (Session session = sessionFactory.getCurrentSession()) {
                return session.createQuery("SELECT u from User u LEFT join Account a on u.id = a.user.id", User.class).list();
            }
        });
    }

}
