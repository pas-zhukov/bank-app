package ru.pas_zhukov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.exception.AccountNotFoundException;
import ru.pas_zhukov.exception.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AccountService {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountProperties accountProperties;

    private int idCounter = 0;

    // Volume
    private final List<Account> accounts = new ArrayList<>();

    public Account createAccount(int userId) {
        Account account = new Account(idCounter, userId,
                userService.getUserById(userId).getAccountList().isEmpty() ? accountProperties.getDefaultAmount() : 0);
        idCounter++;
        accounts.add(account);
        userService.getUserById(userId).addAccount(account);
        return account;
    }

    public Account createAccount(User user) {
        return this.createAccount(user.getId());
    }

    public Account getAccountById(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) return account;
        }
        throw new AccountNotFoundException(id);
    }

    public void deposit(Account account, Long amount) {
        account.depositMoney(amount);
    }

    public void withdraw(Account account, Long amount) {
        if (account.getMoneyAmount() < amount) throw new NotEnoughMoneyException(account, amount);
        account.withdrawMoney(amount);
    }

    public void transfer(Account from, Account to, Long amount) {
        if (from.getMoneyAmount() < amount) throw new NotEnoughMoneyException(from, amount);
        double coefficient = 1.d;
        if (!Objects.equals(from.getUserId(), to.getUserId())) coefficient = (1.d - accountProperties.getTransferCommission());
        from.withdrawMoney(amount);
        long moneyToDeposit = (long) (amount * coefficient);
        to.depositMoney(moneyToDeposit);
    }

    public void deleteAccount(Account account) {
        userService.getUserById(account.getUserId()).removeAccount(account);
        accounts.remove(account);
    }
}
