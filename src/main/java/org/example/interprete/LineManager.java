package org.example.interprete;

public class LineManager {
    private final Input input;
    private final Output output;
    private final Runner runner;
    private final StringBuilder stringBuilder;
    private String currentLine = "";
    public static final String STATEMENT_SYMBOL = "...";
    public static final String LINE = "\n";
    public static final String EXIT_COMMAND = "exit()";
    public static final String COLON_SYMBOL = ":";

    public LineManager() {
        output = new Output();
        input = new Input();
        runner = new Runner();
        stringBuilder = new StringBuilder();
    }

    public void inputLineRunner() {
        while (!lineIsExit()) {
            if (inputIsFunction()) {
                defineFunction(stringBuilder);
            }
            runner.retrieveResult(currentLine);
            currentLine = input.fromConsole();
        }
    }

    private boolean lineIsExit() {
        return currentLine.equals(EXIT_COMMAND);
    }

    private boolean inputIsFunction() {
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
