package org.example.interprete.engine;

import org.example.Input;
import org.example.Output;
import org.example.python.PyHighLevelCaller;

public class EmbeddedPython {
    private final PyHighLevelCaller pyHighLevelCaller;
    private final Output output;
    private final InputLineManager inputLineManager;

    public EmbeddedPython(Input inputChanel, Output outputChanel) {
        pyHighLevelCaller = new PyHighLevelCaller(outputChanel);
        output = outputChanel;
        inputLineManager = new InputLineManager(inputChanel, output, pyHighLevelCaller);
    }

    public void initialize() {
        output.toConsole(pyHighLevelCaller.welcomeMessage());
        inputLineManager.readLinesFromUser();
        pyHighLevelCaller.quit();
    }
}
