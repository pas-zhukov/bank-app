package ru.pas_zhukov.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.util.TransactionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AccountService {

    private final AccountProperties accountProperties;
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public AccountService(AccountProperties accountProperties, TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.accountProperties = accountProperties;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Account createAccount(int userId) {
        User user;
        try (Session session = sessionFactory.getCurrentSession()) { // TODO: лучше бы вызывать сервис...
            user = session.get(User.class, userId);
        }
        return createAccount(user);
    }

    public Account createAccount(User user) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            Account account = new Account(user,
                    user.getAccountList().isEmpty() ? accountProperties.getDefaultAmount() : 0);
            session.persist(account);
            return account;
        });
    }

    public Account getAccountById(int id) {
           Session session = sessionFactory.getCurrentSession();
            return session.get(Account.class, id);
//                new IllegalArgumentException("Account with id " + id + " not found"));
    }

    public void deposit(Account account, Long amount) {
        account.depositMoney(amount);
    }

    public void deposit(int accountId, Long amount) {
        Account account = getAccountById(accountId);
        deposit(account, amount);
    }

    public void withdraw(Account account, Long amount) {
        if (account.getMoneyAmount() < amount) {
            throwNotEnoughMoneyException(account, amount);
        }
        account.withdrawMoney(amount);
    }

    public void withdraw(int accountId, Long amount) {
        Account account = getAccountById(accountId);
        withdraw(account, amount);
    }


    public void transfer(Account from, Account to, Long amount) {
        if (from.getMoneyAmount() < amount) {
            throwNotEnoughMoneyException(from, amount);
        }
        double coefficient = 1.d;
        if (!Objects.equals(from.getUserId(), to.getUserId())) {
            coefficient = (1.d - accountProperties.getTransferCommission());
        }
        from.withdrawMoney(amount);
        long moneyToDeposit = (long) (amount * coefficient);
        to.depositMoney(moneyToDeposit);
    }

    public void transfer(int fromId, int toId, Long amount) {
        Account from = getAccountById(fromId);
        Account to = getAccountById(toId);
        transfer(from, to, amount);
    }


    public void deleteAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        transactionHelper.executeInTransaction(() -> {
            session.remove(account);
            return account;
        });
    }

    public void deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        deleteAccount(account);
    }

    private void throwNotEnoughMoneyException(Account account, Long amount) {
        throw new IllegalArgumentException("Not enough money on account with id " + account.getId() + ". " +
                "You requested " + amount + ", " +
                "but there is only " + account.getMoneyAmount() + " on account.");
    }
}
