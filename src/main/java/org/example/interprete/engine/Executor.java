package org.example.interprete.engine;

import org.example.Output;
import org.example.python.PyRunner;
import org.example.python.objects.PyThreadState;

public class Executor {
    private final PyRunner pyRunner;
    private final Output output;
    private PyThreadState state;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";
    public Executor(Output outputChanel, PyRunner pyRunner2) {
        output = outputChanel;
        pyRunner = pyRunner2;
    }

    public void executeLine(String currentLine) {
        if(state != null) {
            pyRunner.restoreThread(state);
        }
        String run = pyRunner.runLine(currentLine);
        state = pyRunner.saveThread();
        String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
        output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
        output.toConsole("");
    }
}