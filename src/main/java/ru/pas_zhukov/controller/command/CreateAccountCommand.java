package ru.pas_zhukov.controller.command;

import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.service.AccountService;
import ru.pas_zhukov.service.UserService;

@Component
public class CreateAccountCommand implements OperationCommand {

    private final AccountService accountService;
    private final UserService userService;
    private final InputScanner inputScanner;

    public CreateAccountCommand(AccountService accountService, UserService userService, InputScanner inputScanner) {
        this.accountService = accountService;
        this.userService = userService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        System.out.println("Please enter user id for whom to create account:");
        int userId = inputScanner.parseInteger();
        Account account = accountService.createAccount(userService.getUserByIdOrThrow(userId));
        System.out.println("Account created: " + account);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
