package org.example.interprete.io;

import org.example.interprete.Input;

import java.util.Scanner;

public class InputConsole implements Input {
    private final Scanner scanner;

    public InputConsole() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String fromConsole() {
        return scanner.nextLine();
    }
}
