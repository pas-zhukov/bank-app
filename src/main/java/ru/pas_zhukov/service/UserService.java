package ru.pas_zhukov.service;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.util.TransactionHelper;

import java.util.List;

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
        return transactionHelper.executeInTransaction(() ->
        {
            Session session = sessionFactory.getCurrentSession();
            if (session.createQuery("SELECT u FROM User u WHERE u.login=:login", User.class).setParameter("login", username).uniqueResult() != null) {
                throw new IllegalArgumentException("User with login " + username + " already exists. Please choose another username.");
            }
            User user = User.builder().withUsername(username).build();
            session.persist(user);
            accountService.createAccount(user);
            session.flush();
            session.refresh(user);
            return user;
        });
    }

    public User getUserByIdOrThrow(int id) throws IllegalArgumentException {
        return transactionHelper.executeInTransaction(() -> {
            try {
                Session session = sessionFactory.getCurrentSession();
                return session.createQuery("SELECT u FROM User u WHERE u.id=:id", User.class).setParameter("id", id).getSingleResult();
            } catch (NoResultException e) {
                throw new IllegalArgumentException("User with id " + id + " not found");
            }
        });

    }

    public List<User> getAllUsers() {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("SELECT u FROM User u LEFT JOIN Account a ON u.id = a.user.id", User.class).list();
        });
    }

}
