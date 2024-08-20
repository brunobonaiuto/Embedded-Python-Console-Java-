package org.example.interprete;

import org.example.python.PyGILState_STATE;
import org.example.python.PyRunner;

public class EmbeddedPython {
    private final PyRunner pyRunner;
    private final Output output;
    private final LineManager lineManager;


    public EmbeddedPython() {
        pyRunner = new PyRunner();
        output = new Output();
        lineManager = new LineManager();
    }

    public void start() {
        output.toConsole(pyRunner.welcomeMessage());
        lineManager.inputLineRunner();
        pyRunner.quit();
    }

}
