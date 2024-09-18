package ru.pas_zhukov.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.config.ConfigurationProperties;
import ru.pas_zhukov.exception.AccountNotFoundException;
import ru.pas_zhukov.exception.NotEnoughMoneyException;
import ru.pas_zhukov.service.AccountService;
import ru.pas_zhukov.service.UserService;

import java.util.Objects;

public class AccountTest {

    public AnnotationConfigApplicationContext context;
    public AccountProperties properties;

    public Long defaultAmount;

    public UserService userService;
    public AccountService accountService;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext("ru.pas_zhukov");
        properties = context.getBean(AccountProperties.class);
        defaultAmount = properties.getDefaultAmount();
        userService = context.getBean(UserService.class);
        accountService = context.getBean(AccountService.class);
    }

    @Test
    public void accountCreationTest() {
        User user = userService.createUser();
        Account account = accountService.createAccount(user);

        assert account!= null;
        assert account.getId() != null;
        assert account.getUserId()!= null;
        assert account.getMoneyAmount() != null;

        assert Objects.equals(account.getUserId(), user.getId());
        assert account.getId() == 0;
        assert Objects.equals(account.getMoneyAmount(), defaultAmount);

        assert user.getAccountList().get(0) == account;
    }

    @Test(expected = AccountNotFoundException.class)
    public void accountDeletionTest() {
        User user = userService.createUser();
        Account account = accountService.createAccount(user);
        int accountId = account.getId();

        accountService.deleteAccount(account);

        assert user.getAccountList().isEmpty();
        Account deletedAccount = accountService.getAccountById(accountId);
    }

    @Test
    public void AccountDepositTest() {
        User user = userService.createUser();
        Account account = accountService.createAccount(user);
        Long amount = 1000L;

        accountService.deposit(account, amount);

        assert Objects.equals(account.getMoneyAmount(), defaultAmount + amount);
    }

    @Test
    public void AccountWithdrawalTest() {
        User user = userService.createUser();
        Account account = accountService.createAccount(user);
        Long amount = 1000L;

        accountService.deposit(account, amount);
        accountService.withdraw(account, amount);

        assert Objects.equals(account.getMoneyAmount(), defaultAmount);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void AccountWithdrawalExceptionTest() {
        User user = userService.createUser();
        Account account = accountService.createAccount(user);
        Long amount = defaultAmount + 1000L;

        accountService.withdraw(account, amount);
    }

}
