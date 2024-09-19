package ru.pas_zhukov.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.entity.User;
import ru.pas_zhukov.exception.LoginNotUniqueException;
import ru.pas_zhukov.service.UserService;

@Component
public class CreateUserCommand implements OperationCommand {

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private InputScanner inputScanner;

    @Override
    public void execute() {
        System.out.println("Enter unique username: ");
        String login = inputScanner.parseLogin();
        try {
            User user = userService.createUser(login);
            System.out.println("User created: " + user);
        } catch (LoginNotUniqueException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
