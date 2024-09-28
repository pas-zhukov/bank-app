package ru.pas_zhukov.controller.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.service.AccountService;

@Component
public class WithdrawAccountCommand implements OperationCommand {

    private final AccountService accountService;
    @Lazy
    private final InputScanner inputScanner;

    public WithdrawAccountCommand(AccountService accountService, InputScanner inputScanner) {
        this.accountService = accountService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        int accountId;

        System.out.println("Please enter id for the account to withdraw from:");
        accountId = inputScanner.parseInteger();

        System.out.println("Please enter amount to withdraw:");
        Long moneyAmount = inputScanner.parseLong();
        accountService.withdraw(accountId, moneyAmount);
        System.out.println("Withdraw successful");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
