package org.example;

import org.example.python.PyRunner;

import java.util.Scanner;

public class InterpreterPython {
    private final PyRunner pyRunner;
    private final Scanner scanner;

    public InterpreterPython() {
        pyRunner = new PyRunner();
        scanner = new Scanner(System.in);
    }
    public void start() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("Welcome to Python");
        System.out.print(">>> ");
        String scan = scanner.nextLine();
        while(!scan.equals("exit")){
            if(scan.contains(":")){
                while(!scan.isEmpty()) {
                    stringBuilder.append(scan);
                    System.out.print("...\t");
                    scan = scanner.nextLine();
                }
        }
            runLine(scan);
            scan = scanner.nextLine();
    }
        pyRunner.quit();

    }

    private void runLine(String scan) {
        String result = pyRunner.runLine(scan);
        if(result.isBlank()){
            System.out.print(">>> ");
        }else {
            System.out.println(result);
            System.out.print(">>> ");
        }
    }

}
