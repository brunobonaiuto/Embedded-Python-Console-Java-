package org.example.interprete;

import org.example.interprete.engine.InputLineManager;
import org.example.interprete.io.DefaultOutput;
import org.example.interprete.io.Output;
import org.example.python.PyRunner;

public class EmbeddedPython {
    private final PyRunner pyRunner;
    private final Output output;
    private final InputLineManager inputLineManager;

    public EmbeddedPython() {
        pyRunner = new PyRunner();
        output = new DefaultOutput();
        inputLineManager = new InputLineManager();
    }

    public void initialize() {
        output.toConsole(pyRunner.welcomeMessage());
        inputLineManager.readLinesFromUser();
        pyRunner.quit();
    }
}
