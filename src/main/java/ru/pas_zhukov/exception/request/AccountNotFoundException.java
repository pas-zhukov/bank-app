package ru.pas_zhukov.exception.request;

import ru.pas_zhukov.exception.BankAppRequestException;

public class AccountNotFoundException extends BankAppRequestException {
    public AccountNotFoundException(int id) {
        super("Account with id " + id + " not found");
    }
}
