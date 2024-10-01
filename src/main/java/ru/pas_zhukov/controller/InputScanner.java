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

    public int parseInteger() throws IllegalArgumentException {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter Integer value.");
        }
    }

    public Long parseLong() throws IllegalArgumentException {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter Long value.");
        }
    }
}
