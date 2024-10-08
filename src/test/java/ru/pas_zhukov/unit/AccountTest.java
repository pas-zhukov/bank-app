package ru.pas_zhukov.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.service.AccountService;
import ru.pas_zhukov.service.UserService;

import java.util.Objects;

public class AccountTest {

    public AnnotationConfigApplicationContext context;
    public AccountProperties properties;

    public Long defaultAmount;
    public double transferCommission;

    public UserService userService;
    public AccountService accountService;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext("ru.pas_zhukov");
        properties = context.getBean(AccountProperties.class);
        defaultAmount = properties.getDefaultAmount();
        transferCommission = properties.getTransferCommission();
        userService = context.getBean(UserService.class);
        accountService = context.getBean(AccountService.class);
    }

    @Test
    public void accountCreationTest() {
        User user = userService.createUser("2");
        Account account = user.getAccountList().get(0);

        assert account != null;
        assert account.getId() != null;
        assert account.getUserId() != null;
        assert account.getMoneyAmount() != null;

        assert Objects.equals(account.getUserId(), user.getId());
        assert account.getId() == 1;
        assert Objects.equals(account.getMoneyAmount(), defaultAmount);

        assert user.getAccountList().get(0) == account;
    }

    @Test(expected = IllegalArgumentException.class)
    public void lastAccountDeletionTest() {
        User user = userService.createUser("3");
        Account account = user.getAccountList().get(0);
        int accountId = account.getId();

        accountService.deleteAccount(accountId);
        user = userService.getUserByIdOrThrow(user.getId());

        Assert.assertTrue(user.getAccountList().isEmpty());
    }

    @Test
    public void accountDepositTest() {
        User user = userService.createUser("44");
        Account account = user.getAccountList().get(0);
        Long amount = 1000L;

        account = accountService.deposit(account.getId(), amount);

        assert Objects.equals(account.getMoneyAmount(), defaultAmount + amount);
    }

    @Test
    public void accountWithdrawalTest() {
        User user = userService.createUser("5");
        Account account = user.getAccountList().get(0);
        Long amount = 1000L;

        accountService.deposit(account.getId(), amount);
        accountService.withdraw(account.getId(), amount);

        assert Objects.equals(account.getMoneyAmount(), defaultAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void accountWithdrawalExceptionTest() {
        User user = userService.createUser("6");
        Account account = user.getAccountList().get(0);
        Long amount = defaultAmount + 1000L;

        accountService.withdraw(account.getId(), amount);
    }

    @Test
    public void oneUserAccountTransferTest() {
        User user = userService.createUser("7");
        Account account1 = user.getAccountList().get(0);
        Account account2 = accountService.createAccount(user); // пустой, потому что только на первый аккаунт юзера поступают бонусные деньги
        accountService.transfer(account1.getId(), account2.getId(), defaultAmount);
        account1 = accountService.getAccountByIdOrThrow(account1.getId());
        account2 = accountService.getAccountByIdOrThrow(account2.getId());
        assert Objects.equals(account1.getMoneyAmount(), 0L);
        assert Objects.equals(account2.getMoneyAmount(), defaultAmount);
    }

    @Test
    public void differentUserAccountTransferTest() {
        User user = userService.createUser("8");
        User user2 = userService.createUser("9");
        Account account1 = user.getAccountList().get(0);
        Account account2 = user2.getAccountList().get(0);

        accountService.transfer(account1.getId(), account2.getId(), defaultAmount);
        account2 = accountService.getAccountByIdOrThrow(account2.getId());
        assert Objects.equals(account2.getMoneyAmount(), defaultAmount + (long) (defaultAmount * (1 - transferCommission)));
    }

    @Test
    public void getBalanceTest() {
        User user = userService.createUser("90");
        Account account = user.getAccountList().get(0);
        assert Objects.equals(account.getMoneyAmount(), defaultAmount);
    }

    @Test
    public void noBonusMoneyOnSecondAccount() {
        User user = userService.createUser("111");
        Account account2 = accountService.createAccount(user);
        user = userService.getUserByIdOrThrow(user.getId()); // ?
        Assert.assertEquals(2, user.getAccountList().size());
        Assert.assertEquals(Long.valueOf(0L), account2.getMoneyAmount());
    }

    @Test
    public void getAccountById() {
        User user = userService.createUser("1213");
        Account account = accountService.getAccountByIdOrThrow(user.getAccountList().get(0).getId());
        Assert.assertNotNull(account);
    }

}
