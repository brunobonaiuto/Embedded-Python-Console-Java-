package org.example.interprete.engine;

import org.example.interprete.io.DefaultInput;
import org.example.interprete.io.DefaultOutput;
import org.example.interprete.io.Input;
import org.example.interprete.io.Output;

public class InputLineManager {
    private final Input input;
    private final Output output;
    private final Runner runner;
    private final StringBuilder stringBuilder;
    private String currentLine = "";
    public static final String STATEMENT_SYMBOL = "...";
    public static final String LINE = "\n";
    public static final String EXIT_COMMAND = "exit()";
    public static final String COLON_SYMBOL = ":";

    public InputLineManager() {
        output = new DefaultOutput();
        input = new DefaultInput();
        runner = new Runner();
        stringBuilder = new StringBuilder();
    }

    public void readLinesFromUser() {
        while (!userTypedExits()) {
            if (userTypedFunction()) {
                defineFunction(stringBuilder);
            }else {
                runner.executeLine(currentLine);
                currentLine = input.fromConsole();
            }
        }
    }

    private boolean userTypedExits() {
        return currentLine.equals(EXIT_COMMAND);
    }

    private boolean userTypedFunction() {
        return currentLine.endsWith(COLON_SYMBOL);
    }

    private void defineFunction(StringBuilder stringBuilder) {
        while (!currentLine.isEmpty()) {
            stringBuilder.append(currentLine).append(LINE);
            output.toConsole(STATEMENT_SYMBOL);
            currentLine = input.fromConsole();
        }
        currentLine = stringBuilder.toString();
    }
}
