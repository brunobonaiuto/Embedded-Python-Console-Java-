package org.example.python;

import org.example.interprete.io.Output;

import java.util.ArrayList;
import java.util.List;

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
    private final Output outputChanel;
    private int oldSize = 0;

    public PyRunner(Output outputChanel2) {
        outputChanel = outputChanel2;
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        main = pyCaller.initModule(MODULE_NAME);


        // redirect stdout
        // Thread open should be here
        pyCaller.redirectStandardOutput();

        Thread thread = new Thread(() -> {
            while(true) {
                PyGILState_STATE state = pyCaller.unlockGil();
                List stdout = pyCaller.getRedirectedStandardOutput();
                pyCaller.releaseGil(state);
                System.out.println(stdout.size());
                if(stdout.size() > oldSize){
                    //detected output
//                    System.out.println("thread hereee");
//                    System.out.println(stdout.size());
//                    System.out.println("the first:"+stdout.getFirst());
//                    System.out.println("the last:"+stdout.getLast());
//                    System.out.println("\'"+stdout+"\'");
                    outputChanel.toConsole(stdout.getLast().toString()+"\n");
                    oldSize = stdout.size();
                }

            }
        });
        thread.start();
    }

    public String welcomeMessage() {
        return pyCaller.getWelcomeMessage();
    }

    public void quit() {
        pyCaller.destroy();
    }

    public PyGILState_STATE unlockGilState() {
        return pyCaller.unlockGil();
    }

    public void releaseGilState(PyGILState_STATE state) {
        pyCaller.releaseGil(state);
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
            execute(input, PY_FILE_INPUT);
            return BLANK_SYMBOL;
        } else {
            String result = execute(input, PY_EVAL_INPUT);
            //get stdout
            //List stdout = pyCaller.getRedirectedStandardOutput();
            //return result.equals(NONE) ? BLANK_SYMBOL : result;
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
