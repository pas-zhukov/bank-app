package ru.pas_zhukov.service;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.util.TransactionHelper;

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
            if (amount <= 0L) {
                throw new IllegalArgumentException("Money amount must be a positive number.");
            }
            Account account = getAccountByIdOrThrow(accountId);
            account.depositMoney(amount);
            return account;
        });
    }

    public void withdraw(int accountId, Long amount) {
        transactionHelper.executeInTransaction(() -> {
            Account account = getAccountByIdOrThrow(accountId);
            validateMoneyAmountWithdrawal(account, amount);
            account.withdrawMoney(amount);
            return account;
        });
    }

    public void transfer(int fromId, int toId, Long amount) {
        transactionHelper.executeInTransaction(() -> {
            Account from = getAccountByIdOrThrow(fromId);
            Account to = getAccountByIdOrThrow(toId);
            validateMoneyAmountWithdrawal(from, amount);
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
            Account accountToClose = getAccountByIdOrThrow(accountId);
            List<Account> userAccounts = getAllUsersAccountsByUserId(accountToClose.getUserId());
            Account otherUsersAccount = userAccounts.stream()
                    .filter(a -> !Objects.equals(a.getId(), accountToClose.getId()))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Can't delete the last one account."));
            if (accountToClose.getMoneyAmount() > 0) {
                Long amount = accountToClose.getMoneyAmount();
                accountToClose.withdrawMoney(amount);
                otherUsersAccount.depositMoney(amount);
            }
            session.createQuery("DELETE FROM Account a WHERE a.id=:id", Void.class).setParameter("id", accountId).executeUpdate();
            return 0;
        });
    }

    public List<Account> getAllUsersAccountsByUserId(int id) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("SELECT a FROM Account a WHERE a.user.id=:user_id", Account.class).setParameter("user_id", id).list();
        });
    }

    private void validateMoneyAmountWithdrawal(Account account, Long amount) throws IllegalArgumentException {
        if (amount <= 0L) {
            throw new IllegalArgumentException("Money amount must be a positive number.");
        }
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Not enough money on account with id " + account.getId() + ". " +
                    "You requested " + amount + ", " +
                    "but there is only " + account.getMoneyAmount() + " on account.");
        }
    }
}
