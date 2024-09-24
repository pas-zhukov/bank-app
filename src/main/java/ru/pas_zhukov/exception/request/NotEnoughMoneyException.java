package ru.pas_zhukov.exception.request;

import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.exception.BankAppRequestException;

public class NotEnoughMoneyException extends BankAppRequestException {
    public NotEnoughMoneyException(Account account, Long amount) {
        super("Not enough money on account with id " + account.getId() + ". " +
                "You requested " + amount + ", " +
                "but there is only " + account.getMoneyAmount() + " on account.");
    }
}
