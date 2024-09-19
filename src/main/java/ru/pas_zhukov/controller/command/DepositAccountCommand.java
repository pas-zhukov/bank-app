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
public class DepositAccountCommand implements OperationCommand {

    @Autowired
    private AccountService accountService;
    @Autowired
    @Lazy
    private InputScanner inputScanner;

    @Override
    public void execute() {

        Account account;

        System.out.println("Please enter id for the account to deposit:");
        try {
            int accountId = inputScanner.parseId();
            account = accountService.getAccountById(accountId);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (NumberFormatException ex) {
            System.out.println("Please enter an integer ID");
            return;
        }

        System.out.println("Please enter amount to deposit:");
        try {
            Long moneyAmount = inputScanner.parseMoneyAmount();
            accountService.deposit(account, moneyAmount);
            System.out.println("Deposit successful");
        } catch (NumberFormatException ex) {
            System.out.println("Please enter correct money amount");
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
