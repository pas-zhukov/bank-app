package ru.pas_zhukov.unit;

import ru.pas_zhukov.controller.ConsoleOperationType;
import ru.pas_zhukov.controller.InputScanner;
import ru.pas_zhukov.controller.OperationCommand;
import ru.pas_zhukov.controller.OperationsConsoleListener;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OperationsConsoleListenerTest {

    // Invalid command input throws an IllegalArgumentException
    @Test
    public void test_invalid_command_throws_exception() {
        InputScanner mockInputScanner = mock(InputScanner.class);
        when(mockInputScanner.parseString()).thenReturn("INVALID_COMMAND");
    
        List<OperationCommand> commands = Collections.emptyList();
        OperationsConsoleListener listener = new OperationsConsoleListener(mockInputScanner, commands);
    
        try {
            listener.start();
        } catch (IllegalArgumentException e) {
            assertEquals("Command not found: INVALID_COMMAND", e.getMessage());
        }
    }
}