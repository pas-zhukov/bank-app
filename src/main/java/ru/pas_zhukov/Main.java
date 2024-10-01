package ru.pas_zhukov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pas_zhukov.controller.OperationsConsoleListener;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.pas_zhukov");

        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
        consoleListener.start();
    }
}