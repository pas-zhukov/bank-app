package ru.pas_zhukov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.exception.CommandNotFoundException;
import ru.pas_zhukov.exception.LoginNotUniqueException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class InputScanner {

    private final Scanner scanner;

    private final Map<ConsoleOperationType, OperationCommand> commands = new HashMap<>();

    @Autowired
    public InputScanner(List<OperationCommand> commands) {
        scanner = new Scanner(System.in);
        commands.forEach(command -> this.commands.put(command.getOperationType(), command));
    }

    public OperationCommand parseCommand() throws CommandNotFoundException {
        String input = scanner.nextLine().toUpperCase();
        try {
            return commands.get(ConsoleOperationType.valueOf(input));
        } catch (IllegalArgumentException e) {
            throw new CommandNotFoundException(input);
        }
    }

    public String parseLogin() throws LoginNotUniqueException {
        return scanner.nextLine();
    }

    public int parseId() {
        return Integer.parseInt(scanner.nextLine());
    }

    public Long parseMoneyAmount() {
        return Long.parseLong(scanner.nextLine());
    }
}
