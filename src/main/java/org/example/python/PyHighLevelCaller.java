package org.example.python;

import org.example.Output;
import org.example.python.objects.PyObject;
import org.example.python.objects.PyThreadState;
import org.example.python.utils.*;

public class PyHighLevelCaller {

    public static final String MODULE_NAME = "__main__";
    public static final String ASSIGMENT_SYMBOL = "=";
    public static final String COLON_SYMBOL = ":";
    public static final String BLANK_SYMBOL = "";
    public static final String NONE = "None";
    public static final String IMPORT = "import";

    private final StandardOutputRedirect standardOutputRedirect;
    private final PyThreadSaver pyThreadSaver;
    private final PyCaller pyCaller;
    private final PyObject main;

    public PyHighLevelCaller(Output outputChannel) {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        main = pyCaller.initModule(MODULE_NAME);
        pyThreadSaver = new PyThreadSaver(pyCaller);
        standardOutputRedirect = new StandardOutputRedirect(pyCaller, outputChannel);
        startOutputThread();
    }

    private void startOutputThread(){
        Thread stdThread = new Thread(standardOutputRedirect);
        stdThread.start();
    }

    public String welcomeMessage() {
        PyHeader pyHeader = new PyHeader(pyCaller);
        return pyHeader.getWelcomeMessage();
    }

    public void quit() {
        pyCaller.destroy();
    }

    public PyThreadState saveThread(){
        return pyThreadSaver.saveThread();
    }

    public void restoreThread(PyThreadState state){
        pyThreadSaver.restoreThread(state);
    }

    public String runLine(String input) {
        PyEvaluator pyEvaluator = new PyEvaluator(pyCaller, main);
        return pyEvaluator.evaluateLine(input);
    }
}