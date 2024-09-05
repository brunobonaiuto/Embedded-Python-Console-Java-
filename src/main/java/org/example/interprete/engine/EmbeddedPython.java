package org.example.interprete.engine;

import org.example.Input;
import org.example.Output;
import org.example.python.PyRunner;

public class EmbeddedPython {
    private final PyRunner pyRunner;
    private final Output output;
    private final InputLineManager inputLineManager;

    public EmbeddedPython(Input inputChanel, Output outputChanel) {
        pyRunner = new PyRunner(outputChanel);
        output = outputChanel;
        inputLineManager = new InputLineManager(inputChanel, output, pyRunner);
    }

    public void initialize() {
        output.toConsole(pyRunner.welcomeMessage());
        inputLineManager.readLinesFromUser();
        pyRunner.quit();
    }
}
