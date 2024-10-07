package ru.pas_zhukov.entity.builder;

import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.entity.User;


public class AccountBuilder {
    private final Account account;

    public AccountBuilder() {
        account = new Account(null, null, 0L);
    }

    public AccountBuilder withUser(User user) {
        account.setUser(user);
        return this;
    }

    public AccountBuilder withMoneyAmount(Long amount) {
        account.setMoneyAmount(amount);
        return this;
    }

    public Account build() {
        return account;
    }
}
