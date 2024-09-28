package ru.pas_zhukov.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.exception.request.AccountNotFoundException;
import ru.pas_zhukov.service.AccountService;
import ru.pas_zhukov.service.UserService;

@Component
public class CloseAccountCommand implements OperationCommand {

    private final UserService userService;
    @Lazy
    private final InputScanner inputScanner;

    public CloseAccountCommand(UserService userService, InputScanner inputScanner) {
        this.userService = userService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        System.out.println("Please enter id for the account to close:");
        int accountId = inputScanner.parseId();
        userService.deleteAccount(accountId);
        System.out.println("Account was closed.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
