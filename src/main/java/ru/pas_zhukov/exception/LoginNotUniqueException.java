package ru.pas_zhukov.exception;

public class LoginNotUniqueException extends RuntimeException {
    public LoginNotUniqueException(String login) {
        super("User with login " + login + " already exists. Please choose another username.");
    }
}
