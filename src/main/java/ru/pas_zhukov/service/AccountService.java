package ru.pas_zhukov.service;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.util.TransactionHelper;

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

    public Account createAccount(User user) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            Account account = Account.builder()
                    .withUser(user)
                    .withMoneyAmount(user.getAccountList().isEmpty() ? accountProperties.getDefaultAmount() : 0)
                    .build();
            session.persist(account);
            return account;
        });
    }

    public Account getAccountByIdOrThrow(int id) throws IllegalArgumentException {
        return transactionHelper.executeInTransaction(() -> {
            try {
                Session session = sessionFactory.getCurrentSession();
                return session.createQuery("SELECT a FROM Account a WHERE a.id=:id", Account.class).setParameter("id", id).getSingleResult();
            } catch (NoResultException e) {
                throw new IllegalArgumentException("Account with id " + id + " not found.");
            }
        });
    }

    public Account deposit(int accountId, Long amount) {
        return transactionHelper.executeInTransaction(() -> {
            Account account = getAccountByIdOrThrow(accountId);
            account.depositMoney(amount);
            return account;
        });
    }

    public Account withdraw(int accountId, Long amount) {
        return transactionHelper.executeInTransaction(() -> {
            Account account = getAccountByIdOrThrow(accountId);
            if (account.getMoneyAmount() < amount) {
                throwNotEnoughMoneyException(account, amount);
            }
            account.withdrawMoney(amount);
            return account;
        });
    }

    public void transfer(int fromId, int toId, Long amount) {
        transactionHelper.executeInTransaction(() -> {
            Account from = getAccountByIdOrThrow(fromId);
            Account to = getAccountByIdOrThrow(toId);
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
            return 0;
        });
    }


    public void deleteAccount(int accountId) {
        transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            Account accountToDelete = getAccountByIdOrThrow(accountId);
            session.createQuery("DELETE FROM Account a WHERE a.id=:id", Void.class).setParameter("id", accountId).executeUpdate();
            return 0;
        });
    }

    private void throwNotEnoughMoneyException(Account account, Long amount) {
        throw new IllegalArgumentException("Not enough money on account with id " + account.getId() + ". " +
                "You requested " + amount + ", " +
                "but there is only " + account.getMoneyAmount() + " on account.");
    }
}
