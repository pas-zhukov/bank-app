package ru.pas_zhukov.controller;

public interface OperationCommand {
    void execute();

    ConsoleOperationType getOperationType();
}
