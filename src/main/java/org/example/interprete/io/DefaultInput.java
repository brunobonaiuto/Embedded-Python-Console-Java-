package org.example.interprete.io;

import java.util.Scanner;

public class DefaultInput implements Input {
    private final Scanner scanner;

    public DefaultInput() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String fromConsole() {
        return scanner.nextLine();
    }
}
