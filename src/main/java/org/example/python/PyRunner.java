package org.example.python;

import org.example.Output;
import org.example.python.objects.PyGILState_STATE;
import org.example.python.objects.PyObject;
import org.example.python.objects.PyThreadState;

public class PyRunner {
    private final PyCaller pyCaller;
    private final PyObject main;
    public static final String MODULE_NAME = "__main__";
    private final int PY_EVAL_INPUT = 258;
    private final int PY_FILE_INPUT = 257;
    private final int PY_SINGLE_INPUT = 256;
    public static final String ASSIGMENT_SYMBOL = "=";
    public static final String COLON_SYMBOL = ":";
    public static final String BLANK_SYMBOL = "";
    public static final String NONE = "None";
    public static final String IMPORT = "import";
    private final StandardOutputRedirect standardOutputRedirect;
    private final ErrorManager errorManager;

    public PyRunner(Output outputChannel) {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        errorManager = new ErrorManager(pyCaller);
        main = pyCaller.initModule(MODULE_NAME);
        standardOutputRedirect = new StandardOutputRedirect(pyCaller, outputChannel);
        startOutputThread();
    }

    private void startOutputThread(){
        Thread stdThread = new Thread(standardOutputRedirect);
        stdThread.start();
    }

    public String welcomeMessage() {
        return pyCaller.getWelcomeMessage();
    }

    public void quit() {
        pyCaller.destroy();
    }

    public PyThreadState saveThread(){
        return pyCaller.EvalSaveThread();
    }

    public void restoreThread(PyThreadState state){
        pyCaller.EvalRestoreThread(state);
    }

    public String runLine(String input) {
        String result;
        int numberOfAssigmentSymbol = input.length() - input.replace(ASSIGMENT_SYMBOL, BLANK_SYMBOL).length();
        if (isStatementLine(input, numberOfAssigmentSymbol)) {
            result = execute(input, PY_FILE_INPUT);
        } else {
            result = execute(input, PY_EVAL_INPUT);
        }
        return result.equals(NONE) ? BLANK_SYMBOL : result;
    }

    private boolean isStatementLine(String input, int numberOfAssigmentSymbol) {
        return numberOfAssigmentSymbol == 1 || input.isBlank() || input.contains(COLON_SYMBOL) || input.contains(IMPORT);
    }

    private String execute(String inputLineFromConsole, int input_type) {
        try {
            PyObject code = pyCaller.compileString(inputLineFromConsole, input_type);
            PyObject evalResult = pyCaller.eval(code, pyCaller.getModuleDict(main), pyCaller.getModuleDict(main));
            return pyCaller.toString(evalResult);
        } catch (IllegalArgumentException e) {
            return errorManager.getStringError();
        }
    }
}