package org.example;

import org.example.python.PyRunner;

import java.util.Scanner;

public class InterpretePython {
    private PyRunner pyRunner;

    public InterpretePython() {
        pyRunner = new PyRunner();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Welcome to Python\n");
        System.out.print(">>> ");
        String scan = scanner.nextLine();
        while(!scan.equals("exit")){
            String result = pyRunner.runLine(scan);
            System.out.println(result);
            System.out.print(">>> ");
            scan = scanner.nextLine();
        }

    }

}
