package org.example.python;

import org.example.interprete.io.Output;

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
    private String result = "";

    public PyRunner(Output outputChannel) {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
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
        int numberOfAssigmentSymbol = input.length() - input.replace(ASSIGMENT_SYMBOL, BLANK_SYMBOL).length();
        if (isStatementLine(input, numberOfAssigmentSymbol)) {
            Thread thread1 = new Thread(()-> {
                PyGILState_STATE state = pyCaller.unlockGil();
                execute(input, PY_FILE_INPUT);
                pyCaller.releaseGil(state);
            });
           thread1.start();
            return BLANK_SYMBOL;
        } else {
            Thread thread2 = new Thread(() -> {
                PyGILState_STATE state = pyCaller.unlockGil();
                result = execute(input, PY_EVAL_INPUT);
                pyCaller.releaseGil(state);
            });
            thread2.start();
            return result.equals(NONE) ? BLANK_SYMBOL : result;
        }
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
            return getStringError();
        }
    }

    private String getStringError() {
        String message = pyCaller.getFullErrMessage();
        return message.replaceAll("[\\[\\]'\"]", "")
                .replace(", ", "")
                .replaceAll("\\\\n", "\n")
                .trim();
    }
}
