package org.example.interprete.engine;

import org.example.Output;
import org.example.python.PyHighLevelCaller;
import org.example.python.objects.PyThreadState;

public class LineExecutor {
    private final PyHighLevelCaller pyHighLevelCaller;
    private final Output output;
    private PyThreadState state;
    public static final String EXPRESSION_SYMBOL = ">>> ";
    public static final String LINE = "\n";
    public LineExecutor(Output outputChanel, PyHighLevelCaller pyHighLevelCaller2) {
        output = outputChanel;
        pyHighLevelCaller = pyHighLevelCaller2;
    }

    public void run(String currentLine) {
        if(state != null) {
            pyHighLevelCaller.restoreThread(state);
        }
        String run = pyHighLevelCaller.runLine(currentLine);
        state = pyHighLevelCaller.saveThread();
        String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
        output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
        output.toConsole("");
    }
}