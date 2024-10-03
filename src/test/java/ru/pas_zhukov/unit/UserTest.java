package ru.pas_zhukov.unit;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pas_zhukov.config.AccountProperties;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserTest {

    public AnnotationConfigApplicationContext context;
    public AccountProperties properties;

    public Long defaultAmount;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext("ru.pas_zhukov");
        properties = context.getBean(AccountProperties.class);
        defaultAmount = properties.getDefaultAmount();
    }

    @Test
    public void userIdAndLoginNotNullTest() {
        User user = context.getBean(UserService.class).createUser("1");
        assert user.getId() != null;
        assert user.getLogin() != null;
    }

    @Test
    public void usersHaveUniqueIdTest() {
        IntStream.range(0, 10).forEach( e -> context.getBean(UserService.class).createUser("user" + e));
        List<User> users = context.getBean(UserService.class).getAllUsers();
        Set<Integer> ids = users.stream().map(User::getId).collect(Collectors.toSet());
        assert ids.size() == 10;
    }

    @Test
    public void getAllUsersTest() {
        IntStream.range(0, 10).forEach( e -> context.getBean(UserService.class).createUser("user" + e));
        List<User> users = context.getBean(UserService.class).getAllUsers();
        assert users.size() == 10;
    }

    @Test
    public void getUserByIdTest() {
        IntStream.range(0, 10).forEach( e -> context.getBean(UserService.class).createUser("user2" + e));
        User user = context.getBean(UserService.class).getUserById(5);
        assert user.getId() == 5;
    }

    @Test(expected = IllegalArgumentException.class)
    public void userLoginNotUniqueTest() {
        User user = context.getBean(UserService.class).createUser("user");
        User user2 = context.getBean(UserService.class).createUser("user");
    }

    @Test
    public void userHaveOneAccountOnCreation() {
        User user = context.getBean(UserService.class).createUser("15");
        assert user.getAccountList().size() == 1;
        assert Objects.equals(user.getAccountList().get(0).getMoneyAmount(), defaultAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notExistingUserTest() {
        context.getBean(UserService.class).getUserById(9999);
    }

}
