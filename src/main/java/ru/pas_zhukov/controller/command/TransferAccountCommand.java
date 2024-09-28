package ru.pas_zhukov.controller.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.service.AccountService;

@Component
public class TransferAccountCommand implements OperationCommand {

    private final AccountService accountService;
    @Lazy
    private final InputScanner inputScanner;

    public TransferAccountCommand(AccountService accountService, InputScanner inputScanner) {
        this.accountService = accountService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        int accountIdFrom;
        int accountIdTo;

        System.out.println("Please enter id for the account to withdraw from:");
        accountIdFrom = inputScanner.parseInteger();
        System.out.println("Please enter id for the account to deposit to:");
        accountIdTo = inputScanner.parseInteger();

        System.out.println("Please enter amount to transfer:");
        Long moneyAmount = inputScanner.parseLong();
        accountService.transfer(accountIdFrom, accountIdTo, moneyAmount);
        System.out.println("Transfer successful");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
