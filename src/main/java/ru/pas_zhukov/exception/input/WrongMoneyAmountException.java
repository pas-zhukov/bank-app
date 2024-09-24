package ru.pas_zhukov.exception.input;

import ru.pas_zhukov.exception.BankAppInputException;

public class WrongMoneyAmountException extends BankAppInputException {
    public WrongMoneyAmountException(String message) {
        super(message);
    }
}