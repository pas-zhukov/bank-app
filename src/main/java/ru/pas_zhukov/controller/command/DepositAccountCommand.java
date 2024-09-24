package ru.pas_zhukov.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.exception.request.AccountNotFoundException;
import ru.pas_zhukov.service.AccountService;

@Component
public class DepositAccountCommand implements OperationCommand {

    @Autowired
    private AccountService accountService;
    @Autowired
    @Lazy
    private InputScanner inputScanner;

    @Override
    public void execute() {
        int accountId;
        System.out.println("Please enter id for the account to deposit:");
        accountId = inputScanner.parseId();
        System.out.println("Please enter amount to deposit:");
        Long moneyAmount = inputScanner.parseMoneyAmount();
        accountService.deposit(accountId, moneyAmount);
        System.out.println("Deposit successful");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
