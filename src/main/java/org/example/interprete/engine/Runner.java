package org.example.interprete.engine;

import org.example.interprete.io.Output;
import org.example.python.PyGILState_STATE;
import org.example.python.PyRunner;
import org.example.python.PyThreadState;

public class Runner {
    private final PyRunner pyRunner;
    private final Output output;
    private PyThreadState state;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";
    public Runner(Output outputChanel, PyRunner pyRunner2) {
        output = outputChanel;
        pyRunner = pyRunner2;
    }

    public void executeLine(String currentLine) {
        if(state != null) {
            pyRunner.restoreThread(state);
        }
        Thread thread = new Thread(()->{
            PyGILState_STATE state1 = pyRunner.unlockGilState();
            String run = pyRunner.runLine(currentLine);
            pyRunner.releaseGilState(state1);
            String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
        });
        thread.start();
        state = pyRunner.saveThread();

    }
}
