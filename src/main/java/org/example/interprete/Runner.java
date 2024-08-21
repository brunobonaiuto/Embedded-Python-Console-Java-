package org.example.interprete;


import org.example.python.PyRunner;
import org.example.python.PyThreadState;

public class Runner {
    private final PyRunner pyRunner;
    private final Output output;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";

    private PyThreadState state;

    public Runner() {
        output = new Output();
        pyRunner = new PyRunner();
    }

    public void retrieveResult(String currentLine) {
        if(state != null) {
            pyRunner.restoreThread(state);
        }
        //PyGILState_STATE gState = pyRunner.unlockGilState();
        String run = pyRunner.runLine(currentLine);
        //pyRunner.releaseGilState(gState);
        state = pyRunner.saveThread();
        String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
        output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
    }
}
