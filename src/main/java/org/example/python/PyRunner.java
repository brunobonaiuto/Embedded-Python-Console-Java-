package org.example.python;

import com.sun.jna.Library;

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

    public PyRunner() {
        pyCaller = new PyCaller();
        pyCaller.initializePython();
        main = pyCaller.initModule(MODULE_NAME);
    }

    public String runLine(String input) {
        int numberOfAssigmentSymbol = input.length() - input.replace(ASSIGMENT_SYMBOL, BLANK_SYMBOL).length();
        if(isStatementLine(input, numberOfAssigmentSymbol)){
            execute(input, PY_FILE_INPUT);
            return BLANK_SYMBOL;
        }else{
           String result = execute(input, PY_EVAL_INPUT);
           return result.equals(NONE) ? BLANK_SYMBOL : result;
        }
    }

    private boolean isStatementLine(String input, int numberOfAssigmentSymbol) {
        return numberOfAssigmentSymbol == 1 || input.isBlank() || input.contains(COLON_SYMBOL) || input.contains(IMPORT);
    }

    private String execute(String inputLineFromConsole, int input_type) {
        try{
            PyObject code = pyCaller.compileString(inputLineFromConsole, input_type);
            PyObject evalResult = pyCaller.eval(code, pyCaller.getModuleDict(main), pyCaller.getModuleDict(main));
            return pyCaller.toString(evalResult);
        }catch (IllegalArgumentException e){
            return pyCaller.clearException();
        }
    }

    public void quit() {
        pyCaller.destroy();
    }
}
