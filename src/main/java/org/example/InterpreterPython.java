package org.example;

import org.example.python.PyRunner;

import java.util.Scanner;

public class InterpreterPython {
    private final PyRunner pyRunner;
    private final Scanner scanner;
    private String input;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String WELCOME_MESSAGE = "Welcome to Python\n" + EXPRESSION_SYMBOL;
    public static final String STATEMENT_SYMBOL = "...\t";
    public static final String LINE = "\n";

    public static final String EXIT_COMMAND = "exit()";
    public static final String FUNCTION_SYMBOL = ":";

    public InterpreterPython() {
        pyRunner = new PyRunner();
        scanner = new Scanner(System.in);
    }

    public void start() {
        printMessage(WELCOME_MESSAGE);
        input = getInputFromConsole();
        evaluateInput();
        pyRunner.quit();
    }

    private void printMessage(String output) {
        System.out.print(output);
    }

    private String getInputFromConsole() {
        return scanner.nextLine();
    }

    private void evaluateInput() {
        StringBuilder stringBuilder = new StringBuilder();
        while(!input.equals(EXIT_COMMAND)){
            if(inputIsFunction()){
                defineFunction(stringBuilder);
            }
            printMessage(retrieveResult());
            input = getInputFromConsole();
        }
    }

    private boolean inputIsFunction() {
        return input.endsWith(FUNCTION_SYMBOL);
    }

    private void defineFunction(StringBuilder stringBuilder) {
        while(!input.isEmpty()) {
            stringBuilder.append(input).append(LINE);
            printMessage(STATEMENT_SYMBOL);
            input = getInputFromConsole();
        }
        input = stringBuilder.toString();
    }
    private String retrieveResult() {
        return pyRunner.runLine(input).isBlank() ? EXPRESSION_SYMBOL : pyRunner.runLine(input) + LINE + EXPRESSION_SYMBOL;
    }
}
