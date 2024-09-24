package ru.pas_zhukov.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.exception.request.UserNotFoundException;
import ru.pas_zhukov.service.AccountService;
import ru.pas_zhukov.service.UserService;

@Component
public class CreateAccountCommand implements OperationCommand {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private InputScanner inputScanner;

    @Override
    public void execute() {
        System.out.println("Please enter user id for whom to create account:");
        int userId = inputScanner.parseId();
        Account account = accountService.createAccount(userId);
        System.out.println("Account created: " + account);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
