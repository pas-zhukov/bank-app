package ru.pas_zhukov.exception.input;

import ru.pas_zhukov.exception.BankAppInputException;

public class CommandNotFoundException extends BankAppInputException {
    public CommandNotFoundException(String command) {
        super("Command not found: " + command);
    }
}
