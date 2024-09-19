package ru.pas_zhukov.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.entity.Account;
import ru.pas_zhukov.exception.AccountNotFoundException;
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
        try {
            int accountId = inputScanner.parseId();
            Account account = accountService.getAccountById(accountId);
            accountService.deleteAccount(account);
            System.out.println("Account was closed.");
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Please enter an integer ID");
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
