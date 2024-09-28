package ru.pas_zhukov.controller;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputScanner {

    private final Scanner scanner;

    public InputScanner() {
        scanner = new Scanner(System.in);

    }

    public String parseString() {
        return scanner.nextLine();
    }

    public int parseId() throws NumberFormatException{
        return Integer.parseInt(scanner.nextLine());
    }

    public Long parseMoneyAmount() throws NumberFormatException{
        return Long.parseLong(scanner.nextLine());
    }
}
