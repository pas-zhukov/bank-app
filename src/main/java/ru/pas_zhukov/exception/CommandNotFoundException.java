package ru.pas_zhukov.exception;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String command) {
        super("Command not found: " + command);
    }
}
