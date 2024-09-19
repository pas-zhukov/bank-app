package ru.pas_zhukov.exception;

import ru.pas_zhukov.entity.Account;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(Account account, Long amount) {
        super("Not enough money on account with id " + account.getId() + ". " +
                "You requested " + amount + ", " +
                "but there is only " + account.getMoneyAmount() + " on account.");
    }
}
