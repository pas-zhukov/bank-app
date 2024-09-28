package ru.pas_zhukov.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.exception.request.LoginNotUniqueException;
import ru.pas_zhukov.service.UserService;

@Component
public class CreateUserCommand implements OperationCommand {

    private final UserService userService;

    @Lazy
    private final InputScanner inputScanner;

    public CreateUserCommand(UserService userService, InputScanner inputScanner) {
        this.userService = userService;
        this.inputScanner = inputScanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter unique username: ");
        String login = inputScanner.parseLogin();
        User user = userService.createUser(login);
        System.out.println("User created: " + user);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
