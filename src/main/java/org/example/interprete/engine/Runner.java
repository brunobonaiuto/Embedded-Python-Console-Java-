package org.example.interprete.engine;

import org.example.interprete.io.Output;
import org.example.python.PyRunner;
import org.example.python.PyThreadState;

public class Runner {
    private final PyRunner pyRunner;
    private final Output output;
    private PyThreadState state;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";
    public Runner(Output outputChanel) {
        output = outputChanel;
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
