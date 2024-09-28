package ru.pas_zhukov.controller.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.service.AccountService;

@Component
public class CreateAccountCommand implements OperationCommand {

    private final AccountService accountService;
    @Lazy
    private final InputScanner inputScanner;

    public CreateAccountCommand(AccountService accountService, InputScanner inputScanner) {
        this.accountService = accountService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        System.out.println("Please enter user id for whom to create account:");
        int userId = inputScanner.parseInteger();
        Account account = accountService.createAccount(userId);
        System.out.println("Account created: " + account);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
