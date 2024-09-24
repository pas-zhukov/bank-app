package ru.pas_zhukov.exception.request;

import ru.pas_zhukov.exception.BankAppRequestException;

public class UserNotFoundException extends BankAppRequestException {
    public UserNotFoundException(int id) {
        super("User with id " + id + " not found");
    }
}
