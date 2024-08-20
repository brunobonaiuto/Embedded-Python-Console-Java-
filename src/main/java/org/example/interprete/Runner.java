package org.example.interprete;

import org.example.python.PyGILState_STATE;
import org.example.python.PyRunner;



public class Runner {
    private final PyRunner pyRunner;
    private final Output output;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";
    public Runner() {
        output = new Output();
        pyRunner = new PyRunner();
    }
    public void retrieveResult(String currentLine) {
        PyGILState_STATE gState = pyRunner.unlockGilState();
        String run = pyRunner.runLine(currentLine);
        pyRunner.releaseGilState(gState);
        String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
        output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
    }
}
