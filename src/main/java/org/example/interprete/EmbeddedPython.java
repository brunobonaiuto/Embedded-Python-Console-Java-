package org.example.interprete;

import org.example.interprete.engine.InputLineManager;
import org.example.interprete.io.DefaultOutput;
import org.example.interprete.io.Input;
import org.example.interprete.io.Output;
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
