package ru.pas_zhukov.exception.input;

import ru.pas_zhukov.exception.BankAppInputException;

public class WrongIdException extends BankAppInputException {
    public WrongIdException(String message) {
        super(message);
    }
}