package org.example.interprete;

import org.example.python.PyRunner;
import org.example.python.PyThreadState;

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
        //Save
        //PyThreadState state = pyRunner.saveThread();
        lineManager.inputLineRunner();
        //Restore
        //pyRunner.restoreThread(state);
        pyRunner.quit();
    }
}
