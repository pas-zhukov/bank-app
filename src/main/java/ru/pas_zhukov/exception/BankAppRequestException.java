package ru.pas_zhukov.exception;

public class BankAppRequestException extends RuntimeException {
    public BankAppRequestException(String message) {
        super(message);
    }
}
