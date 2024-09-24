package ru.pas_zhukov.exception.request;

import ru.pas_zhukov.exception.BankAppRequestException;

public class LoginNotUniqueException extends BankAppRequestException {
    public LoginNotUniqueException(String login) {
        super("User with login " + login + " already exists. Please choose another username.");
    }
}
