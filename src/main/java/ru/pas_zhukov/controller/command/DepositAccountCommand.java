package ru.pas_zhukov.controller.command;

import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.service.AccountService;

@Component
public class DepositAccountCommand implements OperationCommand {

    private final AccountService accountService;

    private final InputScanner inputScanner;

    public DepositAccountCommand(AccountService accountService, InputScanner inputScanner) {
        this.accountService = accountService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        int accountId;
        System.out.println("Please enter id for the account to deposit:");
        accountId = inputScanner.parseInteger();
        System.out.println("Please enter amount to deposit:");
        Long moneyAmount = inputScanner.parseLong();
        accountService.deposit(accountId, moneyAmount);
        System.out.println("Deposit successful");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
