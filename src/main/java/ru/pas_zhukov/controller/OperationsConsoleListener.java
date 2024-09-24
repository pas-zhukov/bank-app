package ru.pas_zhukov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.exception.BankAppInputException;
import ru.pas_zhukov.exception.BankAppRequestException;
import ru.pas_zhukov.exception.input.CommandNotFoundException;

import java.util.Arrays;

@Component
public class OperationsConsoleListener extends Thread {
    @Autowired
    private InputScanner inputScanner;

    public void run(){
        while (true){
            System.out.println("Enter one of the following commands: ");
            System.out.println(Arrays.toString(ConsoleOperationType.values()));
            System.out.println("\n");
            try {
                OperationCommand operation = inputScanner.parseCommand();
                operation.execute();
            } catch (BankAppInputException | BankAppRequestException ex) {
                System.out.println(ex.getMessage());
            }
            Thread.yield();
        }
    }
}
