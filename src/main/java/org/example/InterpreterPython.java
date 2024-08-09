package org.example;

import org.example.python.PyRunner;

import java.util.Scanner;

public class InterpreterPython {
    private final PyRunner pyRunner;

    public InterpreterPython() {
        pyRunner = new PyRunner();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Python");
        System.out.print(">>> ");
        String scan = scanner.nextLine();
        while(!scan.equals("exit")){
            String result = pyRunner.runLine(scan);
            if(result.isBlank()){
                System.out.print(">>> ");
            }else {
                System.out.println(result);
                System.out.print(">>> ");
            }
            scan = scanner.nextLine();
        }
        pyRunner.quit();

    }

}
