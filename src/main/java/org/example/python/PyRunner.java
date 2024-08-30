package org.example.python;

import org.example.interprete.io.Output;

import static org.example.interprete.engine.Runner.EXPRESSION_SYMBOL;
import static org.example.interprete.engine.Runner.LINE;

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
    private final Output output;
    private String result = "";


    public PyRunner(Output outputChannel2) {
        output = outputChannel2;
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        main = pyCaller.initModule(MODULE_NAME);
        standardOutputRedirect = new StandardOutputRedirect(pyCaller, output);
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

    public void runLine(String input) {
        int numberOfAssigmentSymbol = input.length() - input.replace(ASSIGMENT_SYMBOL, BLANK_SYMBOL).length();
        if (isStatementLine(input, numberOfAssigmentSymbol)) {
            Thread thread1 = new Thread(()-> {
                PyGILState_STATE state = pyCaller.unlockGil();
                execute(input, PY_FILE_INPUT);
//                String resultFromRun = run + LINE + EXPRESSION_SYMBOL;
//                output.toConsole(run.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
                pyCaller.releaseGil(state);
            });
            thread1.start();
            output.toConsole(BLANK_SYMBOL);
//            return BLANK_SYMBOL;
        } else {
            Thread thread2 = new Thread(() -> {
                PyGILState_STATE state = pyCaller.unlockGil();
                result = execute(input, PY_EVAL_INPUT);
                String result2 = result.equals(NONE) ? BLANK_SYMBOL : result;
                String resultFromRun = result2 + LINE + EXPRESSION_SYMBOL;
                output.toConsole(result2.isBlank() ? EXPRESSION_SYMBOL : resultFromRun);
                pyCaller.releaseGil(state);
            });
            thread2.start();
            //System.out.println(result);
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