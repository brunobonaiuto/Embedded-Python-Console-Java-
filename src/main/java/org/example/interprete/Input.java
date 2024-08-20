package org.example.interprete;

import java.util.Scanner;

public class Input {
    private final Scanner scanner;
    public Input() {
        scanner = new Scanner(System.in);
    }
    public String fromConsole() {
        return scanner.nextLine();
    }

}
