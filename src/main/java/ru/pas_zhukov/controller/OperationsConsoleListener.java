package ru.pas_zhukov.controller;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OperationsConsoleListener extends Thread {
    private final InputScanner inputScanner;
    private final Map<ConsoleOperationType, OperationCommand> commands = new HashMap<>();

    public OperationsConsoleListener(InputScanner inputScanner, List<OperationCommand> commands) {
        this.inputScanner = inputScanner;
        commands.forEach(command -> this.commands.put(command.getOperationType(), command));
    }

    public void run() {
        while (true) {
            System.out.println("\nEnter one of the following commands: ");
            System.out.println(Arrays.toString(ConsoleOperationType.values()));
            System.out.println("\n");
            try {
                String input = inputScanner.parseString().toUpperCase();
                OperationCommand operation = getCommand(input);
                operation.execute();
            } catch (IllegalArgumentException ex) {
                System.out.print(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
        }
    }

    public OperationCommand getCommand(String input) {
        try {
            return commands.get(ConsoleOperationType.valueOf(input));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Command not found: " + input);
        }
    }
}
