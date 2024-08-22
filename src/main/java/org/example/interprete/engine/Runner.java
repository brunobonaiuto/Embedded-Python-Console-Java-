package org.example.interprete.engine;

import org.example.interprete.io.DefaultOutput;
import org.example.interprete.io.Output;
import org.example.python.PyRunner;
import org.example.python.PyThreadState;

public class Runner {
    private final PyRunner pyRunner;
    private final Output output;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";

    private PyThreadState state;

    public Runner() {
        output = new DefaultOutput();
        pyRunner = new PyRunner();
    }

    public void executeLine(String currentLine) {
        if(state != null) {
            pyRunner.restoreThread(state);
        }
        String run = pyRunner.runLine(currentLine);
        state = pyRunner.saveThread();
        String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
        output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
    }
}
