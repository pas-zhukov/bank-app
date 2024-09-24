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
public class CloseAccountCommand implements OperationCommand {

    @Autowired
    private AccountService accountService;
    @Autowired
    @Lazy
    private InputScanner inputScanner;

    @Override
    public void execute() {
        System.out.println("Please enter id for the account to close:");
        int accountId = inputScanner.parseId();
        accountService.deleteAccount(accountId);
        System.out.println("Account was closed.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
