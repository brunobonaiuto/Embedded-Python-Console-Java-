package org.example.interprete.engine;

import org.example.interprete.Input;
import org.example.interprete.Output;
import org.example.interprete.engine.InputLineManager;
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
