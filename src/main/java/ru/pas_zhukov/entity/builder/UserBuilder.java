package ru.pas_zhukov.entity.builder;

import ru.pas_zhukov.entity.User;

import java.util.ArrayList;

public class UserBuilder {
    private final User user;

    public UserBuilder() {
        user = new User(null, "empty_username", new ArrayList<>());
    }

    public UserBuilder withUsername(String username) {
        user.setLogin(username);
        return this;
    }

    public User build() {
        return user;
    }
}
