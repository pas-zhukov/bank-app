package ru.pas_zhukov.controller.command;

import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.service.AccountService;

@Component
public class CloseAccountCommand implements OperationCommand {

    private final AccountService accountService;

    private final InputScanner inputScanner;

    public CloseAccountCommand(AccountService accountService, InputScanner inputScanner) {
        this.accountService = accountService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        System.out.println("Please enter id for the account to close:");
        int accountId = inputScanner.parseInteger();
        accountService.deleteAccount(accountId);
        System.out.println("Account was closed.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
