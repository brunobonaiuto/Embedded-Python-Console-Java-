package org.example;

import org.example.python.PyGILState_STATE;
import org.example.python.PyRunner;

import java.util.Scanner;

public class InterpreterPython {
    private final PyRunner pyRunner;
    private final Output output;
    private final Input input;
    private String currentLine = "";
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String STATEMENT_SYMBOL = "...";
    public static final String LINE = "\n";
    public static final String EXIT_COMMAND = "exit()";
    public static final String COLON_SYMBOL = ":";

    public InterpreterPython() {
        pyRunner = new PyRunner();
        output = new Output();
        input = new Input();
    }

    public void start() {
        output.toConsole(pyRunner.welcomeMessage());
        evaluateInput();
        pyRunner.quit();
    }

    private void evaluateInput() {
        StringBuilder stringBuilder = new StringBuilder();
        while(!currentLine.equals(EXIT_COMMAND)){
            if(inputIsFunction()){
                defineFunction(stringBuilder);
            }
            output.toConsole(retrieveResult());
            currentLine = input.fromConsole();
        }
    }

    private boolean inputIsFunction() {
        return currentLine.endsWith(COLON_SYMBOL);
    }

    private void defineFunction(StringBuilder stringBuilder) {
        while(!currentLine.isEmpty()) {
            stringBuilder.append(currentLine).append(LINE);
            output.toConsole(STATEMENT_SYMBOL);
            currentLine = input.fromConsole();
        }
        currentLine = stringBuilder.toString();
    }
    private String retrieveResult() {
        PyGILState_STATE gState = pyRunner.unlockGilState();
        String run = pyRunner.runLine(currentLine);
        pyRunner.releaseGilState(gState);
        String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
        return run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun;
    }
}
